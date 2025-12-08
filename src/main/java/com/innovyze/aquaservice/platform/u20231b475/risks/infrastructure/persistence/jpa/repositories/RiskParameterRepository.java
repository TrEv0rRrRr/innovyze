package com.innovyze.aquaservice.platform.u20231b475.risks.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.aggregates.RiskParameter;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.valueobjects.FailureType;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.valueobjects.InfrastructureType;

/**
 * RiskParameterRepository
 * 
 * @author Valentino Solis
 */
@Repository
public interface RiskParameterRepository extends JpaRepository<RiskParameter, Long> {
  /**
   * Find a risk parameter by infrastructure type and failure type
   * 
   * @param infrastructureType
   * @param failureType
   * @return A {@link RiskParameter} instance if the provided values are valid,
   *         otherwise empty
   */
  Optional<RiskParameter> findByInfrastructureTypeAndFailureType(InfrastructureType infrastructureType,
      FailureType failureType);
}
