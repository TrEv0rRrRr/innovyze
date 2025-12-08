package com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.valueobjects;

import java.util.UUID;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

/**
 * AssetCode VO
 * 
 * @summary
 *          Represents the business unique identifier
 * @author Valentino Solis
 */
@Embeddable
public record AssetCode(@NotBlank(message = "Asset code is required") String assetCode) {
  public AssetCode {
    try {
      UUID.fromString(assetCode);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Asset code must be a valid UUID", e);
    }
  }
}
