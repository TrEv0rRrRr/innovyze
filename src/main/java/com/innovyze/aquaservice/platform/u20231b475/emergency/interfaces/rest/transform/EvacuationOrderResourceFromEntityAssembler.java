package com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.rest.transform;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.aggregates.EvacuationOrder;
import com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.rest.resources.EvacuationOrderResource;

public class EvacuationOrderResourceFromEntityAssembler {
  public static EvacuationOrderResource toResourceFromEntity(EvacuationOrder entity) {
    return new EvacuationOrderResource(
        entity.getId(),
        entity.getAssetCode(),
        entity.getAffectedZones(),
        entity.getReason(),
        entity.getPriority(),
        entity.getIssuedAt(),
        entity.getState(),
        entity.getValidUntil(),
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }
}
