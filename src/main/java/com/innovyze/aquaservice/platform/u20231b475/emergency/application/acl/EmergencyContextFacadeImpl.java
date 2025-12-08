package com.innovyze.aquaservice.platform.u20231b475.emergency.application.acl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.commands.CreateEvacuationOrderCommand;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects.EvacuationOrderPriority;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.services.EvacuationOrderCommandService;
import com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.acl.EmergencyContextFacade;

@Service
public class EmergencyContextFacadeImpl implements EmergencyContextFacade {
  private final EvacuationOrderCommandService service;

  public EmergencyContextFacadeImpl(EvacuationOrderCommandService service) {
    this.service = service;
  }

  @Override
  public Long createEvacuationOrder(String assetCode, List<String> affectedZones, String reason,
      String priority, LocalDateTime issuedAt, LocalDateTime validUntil) {
    var command = new CreateEvacuationOrderCommand(
        assetCode,
        affectedZones,
        reason,
        EvacuationOrderPriority.valueOf(priority),
        issuedAt,
        validUntil);

    return service.handle(command)
        .orElseThrow(() -> new RuntimeException("Failed to create evacuation order"))
        .getId();
  }
}
