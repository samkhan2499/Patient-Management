package com.pm.billingservice.grpc;

import billing.BillingServiceGrpc;
import billing.BillingResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void  createBillingAccount (billing.BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("Received billing request : {}", billingRequest.toString());
        // Business Logic
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("1234556")
                .setStatus("Active")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
