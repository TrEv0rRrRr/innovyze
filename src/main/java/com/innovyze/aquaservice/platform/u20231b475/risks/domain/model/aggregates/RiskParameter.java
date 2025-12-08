package com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.aggregates;

import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.valueobjects.FailureType;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.valueobjects.InfrastructureType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Getter;

/**
 * The Risk Parameter Aggregate Root
 * 
 * @author Valentino Solis
 */
@Entity
public class RiskParameter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Getter
  private InfrastructureType infrastructureType;

  @Enumerated(EnumType.STRING)
  @Getter
  private FailureType failureType;

  @Min(0)
  @Getter
  private Double minImpactValue;

  @Min(0)
  @Getter
  private Double maxImpactValue;

  @Min(0)
  @Getter
  private Double severityThreshold;

  public RiskParameter() {
    // Empty for JPA
  }

  public RiskParameter(InfrastructureType infrastructureType,
      FailureType failureType,
      Double minImpactValue,
      Double maxImpactValue,
      Double severityThreshold) {
    this.infrastructureType = infrastructureType;
    this.failureType = failureType;
    this.minImpactValue = minImpactValue;
    this.maxImpactValue = maxImpactValue;
    this.severityThreshold = severityThreshold;
  }
}
