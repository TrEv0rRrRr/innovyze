package com.innovyze.aquaservice.platform.u20231b475.emergency.domain.services;

import java.util.Optional;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.aggregates.EvacuationOrder;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.commands.CreateEvacuationOrderCommand;

public interface EvacuationOrderCommandService {
  Optional<EvacuationOrder> handle(CreateEvacuationOrderCommand command);
}
