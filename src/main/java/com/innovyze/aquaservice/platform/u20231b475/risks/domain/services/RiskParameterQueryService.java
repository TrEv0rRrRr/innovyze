package com.innovyze.aquaservice.platform.u20231b475.risks.domain.services;

import java.util.Optional;

import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.aggregates.RiskParameter;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.queries.GetRiskParameterByInfrastructureTypeAndFailureType;

/**
 * Service for handle risk parameter queries
 */
public interface RiskParameterQueryService {

  /**
   * Handle GetRiskParameterByInfrastructureTypeAndFailureType query
   * 
   * @param query the query
   * @return A {@link RiskParameter} instance if the query is valid, otherwise
   *         empty
   */
  Optional<RiskParameter> handle(GetRiskParameterByInfrastructureTypeAndFailureType query);
}
