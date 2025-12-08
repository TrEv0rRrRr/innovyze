package com.innovyze.aquaservice.platform.u20231b475.risks.application.internal.eventhandlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.aggregates.RiskParameter;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.valueobjects.FailureType;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.valueobjects.InfrastructureType;
import com.innovyze.aquaservice.platform.u20231b475.risks.infrastructure.persistence.jpa.repositories.RiskParameterRepository;

/**
 * ApplicationReadyEventHandler class
 * This class is used to handle the ApplicationReadyEvent
 */
@Service
public class ApplicationReadyEventHandler {
  @Autowired
  private RiskParameterRepository repo;

  @EventListener
  public void on(ApplicationReadyEvent event) {
    if (repo.count() > 0)
      return;

    repo.saveAll(List.of(
        new RiskParameter(InfrastructureType.WATER_PIPE, FailureType.burst, 0.0, 100.0, 75.0),
        new RiskParameter(InfrastructureType.SEWER_LINE, FailureType.overflow, 0.0, 50.0, 40.0),
        new RiskParameter(InfrastructureType.PUMPING_STATION, FailureType.mechanical_failure, 0.0, 10.0, 8.0),
        new RiskParameter(InfrastructureType.RESERVOIR, FailureType.structural_crack, 0.0, 200.0, 150.0)));
  }
}
