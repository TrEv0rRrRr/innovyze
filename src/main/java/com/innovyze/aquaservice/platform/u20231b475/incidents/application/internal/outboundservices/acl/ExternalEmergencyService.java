package com.innovyze.aquaservice.platform.u20231b475.incidents.application.internal.outboundservices.acl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.acl.EmergencyContextFacade;

/**
 * This service is used to create an Evacuation Order when the
 * IncidentEvaluatedEvent is published.
 */
@Service
public class ExternalEmergencyService {
  private final EmergencyContextFacade facade;

  public ExternalEmergencyService(EmergencyContextFacade facade) {
    this.facade = facade;
  }

  public Long createEvacuationOrder(String assetCode, List<String> affectedZones, String reason,
      String priority,
      LocalDateTime issuedAt, LocalDateTime validUntil) {
    return facade.createEvacuationOrder(assetCode, affectedZones, reason, priority, issuedAt, validUntil);
  }
}
