package com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects.AssetCode;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects.EvacuationOrderPriority;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects.EvacuationOrderState;

public record EvacuationOrderResource(Long id, AssetCode assetCode, List<String> affectedZones, String reason,
                EvacuationOrderPriority priority, LocalDateTime issuedAt, EvacuationOrderState state,
                LocalDateTime validUntil,
                Date createdAt,
                Date updatedAt) {
}
