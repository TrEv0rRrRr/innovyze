package com.innovyze.aquaservice.platform.u20231b475.emergency.application.internal.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.aggregates.EvacuationOrder;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.commands.CreateEvacuationOrderCommand;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.services.EvacuationOrderCommandService;
import com.innovyze.aquaservice.platform.u20231b475.emergency.infrastructure.persistence.jpa.repositories.EvacuationOrderRepository;

@Service
public class EvacuationOrderCommandServiceImpl implements EvacuationOrderCommandService {
  private final EvacuationOrderRepository repo;

  public EvacuationOrderCommandServiceImpl(EvacuationOrderRepository repo) {
    this.repo = repo;
  }

  @Override
  public Optional<EvacuationOrder> handle(CreateEvacuationOrderCommand command) {
    var evacuationOrder = new EvacuationOrder(command);

    repo.save(evacuationOrder);
    return Optional.of(evacuationOrder);
  }
}
