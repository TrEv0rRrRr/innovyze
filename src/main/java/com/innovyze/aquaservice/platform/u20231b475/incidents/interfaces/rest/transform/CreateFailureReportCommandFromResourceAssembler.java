package com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.transform;

import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.commands.CreateFailureReportCommand;
import com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.resources.CreateFailureReportResource;

public class CreateFailureReportCommandFromResourceAssembler {
  public static CreateFailureReportCommand toCommandFromResource(CreateFailureReportResource resource) {
    return new CreateFailureReportCommand(
        resource.assetCode(),
        resource.infrastructureType(),
        resource.failureType(),
        resource.reportedImpactValue(),
        resource.reportedAt(),
        null);
  }
}
