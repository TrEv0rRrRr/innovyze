package com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.rest.resources;

public record RiskParameterResource(String infrastructureType, String failureType, Double minImpactValue,
        Double maxImpactValue, Double severityThreshold) {
}
