package com.pm.patient_service.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PatientResponseDTO(
        UUID id,
        String name,
        String email,
        String address,
        LocalDate dateOfBirth
) {}