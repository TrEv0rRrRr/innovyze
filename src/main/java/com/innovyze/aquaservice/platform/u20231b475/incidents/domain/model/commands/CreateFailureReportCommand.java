package com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command for failure reports creation
 * 
 * @author Valentino Solis
 */
public record CreateFailureReportCommand(String assetCode, String infrastructureType, String failureType,
        Double reportedImpactValue, LocalDateTime reportedAt,
        LocalDateTime resolvedAt) {
}
