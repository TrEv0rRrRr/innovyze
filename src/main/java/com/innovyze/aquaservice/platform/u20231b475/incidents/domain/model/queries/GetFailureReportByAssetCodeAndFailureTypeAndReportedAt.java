package com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.queries;

import java.time.LocalDateTime;

import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.AssetCode;

/**
 * Query to retrieve a failure report by asset code, failure type and reporting
 * date
 */
public record GetFailureReportByAssetCodeAndFailureTypeAndReportedAt(AssetCode assetCode, String failureType,
    LocalDateTime reportedAt) {
}
