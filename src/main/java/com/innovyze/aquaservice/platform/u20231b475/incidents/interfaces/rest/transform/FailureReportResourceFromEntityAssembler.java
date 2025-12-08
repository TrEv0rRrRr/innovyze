package com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.transform;

import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.aggregates.FailureReport;
import com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.resources.FailureReportResource;

public class FailureReportResourceFromEntityAssembler {
  public static FailureReportResource toResourceFromEntity(FailureReport entity) {
    return new FailureReportResource(
        entity.getId(),
        entity.getAssetCode(),
        entity.getInfrastructureType(),
        entity.getFailureType(),
        entity.getReportedImpactValue(),
        entity.getState(),
        entity.getReportedAt(),
        entity.getResolvedAt());
  }
}
