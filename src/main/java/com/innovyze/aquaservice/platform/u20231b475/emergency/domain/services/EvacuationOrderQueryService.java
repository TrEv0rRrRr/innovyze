package com.innovyze.aquaservice.platform.u20231b475.emergency.domain.services;

import java.util.List;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.aggregates.EvacuationOrder;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.queries.GetAllEvacuationOrdersQuery;

public interface EvacuationOrderQueryService {
  List<EvacuationOrder> handle(GetAllEvacuationOrdersQuery query);
}
