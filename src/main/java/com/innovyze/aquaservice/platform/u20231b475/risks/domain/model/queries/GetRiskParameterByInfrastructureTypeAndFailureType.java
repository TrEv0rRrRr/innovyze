package com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.queries;

/**
 * Query to retrieve a risk parameter by infrastructure type and failure type
 */
public record GetRiskParameterByInfrastructureTypeAndFailureType(String infrastructureType,
    String failureType) {
}
