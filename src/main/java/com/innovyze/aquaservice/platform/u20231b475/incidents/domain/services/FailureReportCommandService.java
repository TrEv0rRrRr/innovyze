package com.innovyze.aquaservice.platform.u20231b475.incidents.domain.services;

import java.util.Optional;

import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.aggregates.FailureReport;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.commands.CreateFailureReportCommand;

public interface FailureReportCommandService {
  Optional<FailureReport> handle(CreateFailureReportCommand command);
}
