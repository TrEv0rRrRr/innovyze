package com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.aggregates;

import java.time.LocalDateTime;

import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.commands.CreateFailureReportCommand;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.events.IncidentEvaluatedEvent;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.AssetCode;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.FailureReportState;
import com.innovyze.aquaservice.platform.u20231b475.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Failure Report Aggregate root
 * 
 * @author Valentino Solis
 */
@Getter
@Entity
public class FailureReport extends AuditableAbstractAggregateRoot<FailureReport> {
  @Embedded
  private AssetCode assetCode;

  @NotBlank(message = "The infrastructure type is required")
  private String infrastructureType;

  @NotBlank(message = "The failure type is required")
  private String failureType;

  @NotNull(message = "The reported impact value is required")
  @Min(0)
  private Double reportedImpactValue;

  @Enumerated(EnumType.STRING)
  @Setter
  private FailureReportState state;

  @NotNull(message = "The date when the failure was reported cannot be null")
  @FutureOrPresent
  private LocalDateTime reportedAt;

  @Nullable
  private LocalDateTime resolvedAt;

  public FailureReport() {
    // Empty constructor for JPA
  }

  public FailureReport(CreateFailureReportCommand command, FailureReportState state) {
    this.assetCode = new AssetCode(command.assetCode());
    this.infrastructureType = command.infrastructureType();
    this.failureType = command.failureType();
    this.reportedImpactValue = command.reportedImpactValue();
    this.state = state;
    this.reportedAt = command.reportedAt();

    if (command.resolvedAt() != null) {
      resolve(command.resolvedAt());
    }
  }

  public void resolve(LocalDateTime resolvedAt) {
    if (resolvedAt == null) {
      throw new IllegalArgumentException("Resolved date cannot be null");
    }
    if (resolvedAt.isBefore(this.reportedAt)) {
      throw new IllegalArgumentException("The date when the report was resolved cannot be before the reporting date.");
    }
    this.state = FailureReportState.RESOLVED;
    this.resolvedAt = resolvedAt;
  }

  /**
   * Registers an {@link IncidentEvaluatedEvent} for this failure report, indicating that the incident
   * has been evaluated with the provided severity threshold.
   *
   * @param severityThreshold the threshold value used to evaluate the severity of the incident
   */
  public void incidentEvaluated(Double severityThreshold) {
    this.registerEvent(new IncidentEvaluatedEvent(this, getId(), assetCode.assetCode(), infrastructureType, failureType,
        reportedImpactValue, severityThreshold));
  }
}
