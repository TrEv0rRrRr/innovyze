package com.innovyze.aquaservice.platform.u20231b475.incidents.application.internal.eventhandlers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovyze.aquaservice.platform.u20231b475.incidents.application.internal.outboundservices.acl.ExternalEmergencyService;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.events.IncidentEvaluatedEvent;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.FailureReportState;
import com.innovyze.aquaservice.platform.u20231b475.incidents.infrastructure.persistence.jpa.repositories.FailureReportRepository;

/**
 * Handles {@link IncidentEvaluatedEvent} events by updating the state of the
 * corresponding failure report,
 * calculating the severity and priority of the incident, determining affected
 * zones, and issuing evacuation orders
 * through the {@link ExternalEmergencyService}.
 * <p>
 * The handler avoids loading the aggregate to prevent republishing events, and
 * directly updates the state.
 * Priority and validity of the evacuation order are determined based on the
 * percentage by which the reported
 * impact value exceeds the severity threshold.
 * <ul>
 * <li>If exceed percentage &gt; 15: priority is "URGENT", valid for 72
 * hours.</li>
 * <li>If exceed percentage &gt; 5: priority is "HIGH", valid for 48 hours.</li>
 * <li>Otherwise: priority is "MEDIUM", valid for 24 hours.</li>
 * </ul>
 * Affected zones are calculated based on the impact value:
 * <ul>
 * <li>&gt; 150: Zones A, B, and C</li>
 * <li>&gt; 75: Zones A and B</li>
 * <li>&gt; Otherwise: Zone A</li>
 * </ul>
 * The evacuation order includes asset code, affected zones, reason, priority,
 * and validity period.
 *
 * @author Valentino Solis
 */
@Service
public class IncidentEvaluatedEventHandler {
  private final ExternalEmergencyService emergencyService;
  private final FailureReportRepository repo;

  public IncidentEvaluatedEventHandler(FailureReportRepository repo,
      ExternalEmergencyService emergencyService) {
    this.repo = repo;
    this.emergencyService = emergencyService;
  }

  @EventListener
  @Transactional
  public void on(IncidentEvaluatedEvent event) {
    // Update state directly without loading the aggregate to avoid republishing
    // events
    repo.updateStateById(event.getFailureReportId(), FailureReportState.EVALUATED);

    double exceedPercentage = ((event.getReportedImpactValue() - event.getSeverityThreshold())
        / event.getSeverityThreshold()) * 100;

    String priority;
    LocalDateTime issuedAt = LocalDateTime.now();
    LocalDateTime validUntil;

    if (exceedPercentage > 15) {
      priority = "URGENT";
      validUntil = issuedAt.plusHours(72);
    } else if (exceedPercentage > 5) {
      priority = "HIGH";
      validUntil = issuedAt.plusHours(48);
    } else {
      priority = "MEDIUM";
      validUntil = issuedAt.plusHours(24);
    }

    // saving the affected zones
    List<String> affectedZones = calculateAffectedZones(event.getReportedImpactValue());

    // creating a reason
    String reason = String.format("Failure type: %s, Impact: %.2f",
        event.getFailureType(),
        event.getReportedImpactValue());

    // creating an evacuation order
    emergencyService.createEvacuationOrder(
        event.getAssetCode(),
        affectedZones,
        reason,
        priority,
        issuedAt,
        validUntil);
  }

  // Calculating the affected zones as it says on the pdf
  private List<String> calculateAffectedZones(Double impact) {
    if (impact > 150)
      return List.of("Zone A", "Zone B", "Zone C");
    else if (impact > 75)
      return List.of("Zone A", "Zone B");
    else
      return List.of("Zone A");
  }
}
