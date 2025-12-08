package com.innovyze.aquaservice.platform.u20231b475.emergency.application.internal.queryservices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.aggregates.EvacuationOrder;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.queries.GetAllEvacuationOrdersQuery;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.services.EvacuationOrderQueryService;
import com.innovyze.aquaservice.platform.u20231b475.emergency.infrastructure.persistence.jpa.repositories.EvacuationOrderRepository;

@Service
public class EvacuationOrderQueryServiceImpl implements EvacuationOrderQueryService {
  private final EvacuationOrderRepository repo;

  public EvacuationOrderQueryServiceImpl(EvacuationOrderRepository repo) {
    this.repo = repo;
  }

  @Override
  public List<EvacuationOrder> handle(GetAllEvacuationOrdersQuery query) {
    return repo.findAll();
  }
}
