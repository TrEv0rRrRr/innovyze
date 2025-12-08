package com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.aggregates;

import java.time.LocalDateTime;
import java.util.List;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.commands.CreateEvacuationOrderCommand;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects.AssetCode;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects.EvacuationOrderPriority;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects.EvacuationOrderState;
import com.innovyze.aquaservice.platform.u20231b475.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * The Evacuation Order Aggregate Root
 * 
 * @author Valentino Solis
 */
@Entity
@Getter
public class EvacuationOrder extends AuditableAbstractAggregateRoot<EvacuationOrder> {
  @Embedded
  private AssetCode assetCode;

  @NotNull(message = "The affected zones is required")
  @NotEmpty(message = "The list of affected zones cannot be empty")
  private List<String> affectedZones;

  @NotBlank(message = "The reason is required")
  private String reason;

  @Enumerated(EnumType.STRING)
  private EvacuationOrderPriority priority;

  @NotNull(message = "The issued date cannot be null")
  private LocalDateTime issuedAt;

  @Enumerated(EnumType.STRING)
  private EvacuationOrderState state;

  @NotNull(message = "The evacuation order expiration date cannot be null")
  private LocalDateTime validUntil;

  public EvacuationOrder() {
    // Empty for JPA
  }

  public EvacuationOrder(CreateEvacuationOrderCommand command) {
    this.assetCode = new AssetCode(command.assetCode());
    this.affectedZones = command.affectedZones();
    this.reason = command.reason();
    this.priority = command.priority();
    this.issuedAt = command.issuedAt();
    this.state = EvacuationOrderState.ISSUED;
    this.validUntil = command.validUntil();
    validateDate(command.validUntil());
  }

  public void validateDate(LocalDateTime validUntil) {
    if (validUntil.isBefore(this.issuedAt.plusHours(24))) {
      throw new IllegalArgumentException(
          "The evacuation order expiration date must be at least 24 hours after the issued date.");
    }

    this.validUntil = validUntil;
  }
}
