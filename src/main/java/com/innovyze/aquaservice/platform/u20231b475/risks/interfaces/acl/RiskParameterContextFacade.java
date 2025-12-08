package com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.acl;

import java.util.Optional;

import com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.rest.resources.RiskParameterResource;

/**
 * Risks Context Facade
 */
public interface RiskParameterContextFacade {
  /**
   * Fetch a risk parameter by infrastructure type and failure type
   * 
   * @param infrastructureType
   * @param failureType
   * @return
   */
  Optional<RiskParameterResource> fetchByInfrastructureTypeAndFailureType(String infrastructureType,
      String failureType);
}
