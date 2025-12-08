package com.innovyze.aquaservice.platform.u20231b475.incidents.application.internal.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.incidents.application.internal.outboundservices.acl.ExternalRiskParameterService;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.aggregates.FailureReport;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.commands.CreateFailureReportCommand;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.AssetCode;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.FailureReportState;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.services.FailureReportCommandService;
import com.innovyze.aquaservice.platform.u20231b475.incidents.infrastructure.persistence.jpa.repositories.FailureReportRepository;

@Service
public class FailureReportCommandServiceImpl implements FailureReportCommandService {
  private final FailureReportRepository repo;
  private final ExternalRiskParameterService riskService;

  public FailureReportCommandServiceImpl(FailureReportRepository repo, ExternalRiskParameterService riskService) {
    this.repo = repo;
    this.riskService = riskService;
  }

  @Override
  public Optional<FailureReport> handle(CreateFailureReportCommand command) {
    // Validating before creating a failure report
    var assetCode = new AssetCode(command.assetCode());

    if (repo.existsByAssetCodeAndFailureTypeAndReportedAt(assetCode, command.failureType(), command.reportedAt())) {
      throw new IllegalArgumentException("A failure report for the given properties already exists.");
    }

    // using the ACL
    var riskParameter = riskService
        .fetchByInfrastructureTypeAndFailureType(command.infrastructureType(), command.failureType())
        .orElseThrow(() -> new IllegalArgumentException("Risk parameter not found"));

    // verifying that it is not within normal values
    boolean isFlagged = command.reportedImpactValue() < riskParameter.minImpactValue() ||
        command.reportedImpactValue() > riskParameter.maxImpactValue() ||
        command.reportedImpactValue() > riskParameter.severityThreshold();

    // if everything is ok, then create the failure report
    var failureReport = new FailureReport(command,
        isFlagged ? FailureReportState.FLAGGED : FailureReportState.ACCEPTED);

    // saving on the BD
    repo.save(failureReport);

    // if it's not within normal values, then publish the domain event
    if (isFlagged) {
      failureReport.incidentEvaluated(riskParameter.severityThreshold());
      repo.save(failureReport); // Publish the event
    }

    return Optional.of(failureReport);
  }
}
