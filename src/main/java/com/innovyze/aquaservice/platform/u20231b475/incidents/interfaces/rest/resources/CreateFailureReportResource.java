package com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.resources;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateFailureReportResource(@NotBlank(message = "Asset code is required") String assetCode,
    @NotBlank(message = "The infrastructure type is required") String infrastructureType,
    @NotBlank(message = "The failure type is required") String failureType,
    @NotNull(message = "The reported impact value is required") @Min(0) Double reportedImpactValue,
    @NotNull(message = "The date when the failure was reported cannot be null") @FutureOrPresent LocalDateTime reportedAt,
    LocalDateTime resolvedAt) {
}
