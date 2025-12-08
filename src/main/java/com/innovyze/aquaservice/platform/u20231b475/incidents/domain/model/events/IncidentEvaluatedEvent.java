package com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.events;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

/**
 * Event representing the evaluation of an incident within the system.
 * <p>
 * This event is published when an incident has been evaluated, containing
 * details about the failure report, affected asset, infrastructure type,
 * failure type, reported impact value, and severity threshold.
 * </p>
 *
 * <p>
 * The {@code source} parameter in the constructor is used by JPA to determine
 * which entity published the event.
 * </p>
 *
 * @author Valentino Solis
 */
@Getter
public class IncidentEvaluatedEvent extends ApplicationEvent {
  Long failureReportId;
  String assetCode;
  String infrastructureType;
  String failureType;
  Double reportedImpactValue;
  Double severityThreshold;

  /**
   * Constructs a new IncidentEvaluatedEvent.
   *
   * @param source                the object on which the event initially occurred
   * @param failureReportId       the unique identifier of the failure report
   * @param assetCode             the code representing the affected asset
   * @param infrastructureType    the type of infrastructure involved in the incident
   * @param failureType           the type of failure that occurred
   * @param reportedImpactValue   the reported impact value of the incident
   * @param severityThreshold     the threshold value for severity evaluation
   */
  public IncidentEvaluatedEvent(Object source, Long failureReportId,
      String assetCode,
      String infrastructureType,
      String failureType,
      Double reportedImpactValue,
      Double severityThreshold) {
    super(source);

    this.failureReportId = failureReportId;
    this.assetCode = assetCode;
    this.infrastructureType = infrastructureType;
    this.failureType = failureType;
    this.reportedImpactValue = reportedImpactValue;
    this.severityThreshold = severityThreshold;
  }
}
