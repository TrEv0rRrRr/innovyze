package com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.commands;

import java.time.LocalDateTime;
import java.util.List;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects.EvacuationOrderPriority;

public record CreateEvacuationOrderCommand(String assetCode, List<String> affectedZones, String reason,
        EvacuationOrderPriority priority,
        LocalDateTime issuedAt, LocalDateTime validUntil) {

}
