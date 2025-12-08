package com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.resources;

import java.time.LocalDateTime;

import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.AssetCode;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.FailureReportState;

public record FailureReportResource(Long id, AssetCode assetCode,
                String infrastructureType,
                String failureType,
                Double reportedImpactValue,
                FailureReportState state,
                LocalDateTime reportedAt,
                LocalDateTime resolvedAt) {
}
