package com.pm.localstack;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.rds.*;

public class Localstack extends Stack {
    private final Vpc vpc;

    public Localstack(final App scope, final String id, final StackProps props) {
        super(scope, id, props);
        this.vpc = createVpc();
        DatabaseInstance authServiceDb = createDatabaseInstance("AuthServiceDB", "auth-service-db");
        DatabaseInstance patientServiceDb = createDatabaseInstance("PatientServiceDB", "patient-service-db");
    }

    private Vpc createVpc() {
        return Vpc.Builder
                .create(this, "PatientManagementVpc")
                .vpcName("PatientManagementVpc")
                .maxAzs(2)
                .build();
    }

    private DatabaseInstance createDatabaseInstance(String id, String dbName) {
        return DatabaseInstance.Builder
                .create(this, id)
                .engine(DatabaseInstanceEngine.postgres(
                        PostgresInstanceEngineProps.builder()
                                .version(PostgresEngineVersion.VER_17_2)
                                .build()
                ))
                .vpc(vpc)
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))
                .allocatedStorage(20)
                .credentials(Credentials.fromGeneratedSecret("admin_user"))
                .databaseName(dbName)
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();
    }

    public static void main(final String[] args) {
        App app = new App(AppProps.builder().outdir("./out.cdk").build());

        StackProps props = StackProps.builder().synthesizer(new BootstraplessSynthesizer()).build();

        new Localstack(app, "Localstack", props);
        app.synth();

        System.out.println("CDK app synthesized successfully.");
    }
}
