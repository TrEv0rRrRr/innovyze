package com.innovyze.aquaservice.platform.u20231b475.incidents.application.internal.outboundservices.acl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.acl.RiskParameterContextFacade;
import com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.rest.resources.RiskParameterResource;

/**
 * This service is used to query the bounded context of risks to see whether or
 * not there is a risk parameter with those properties.
 */
@Service
public class ExternalRiskParameterService {
  private final RiskParameterContextFacade facade;

  public ExternalRiskParameterService(RiskParameterContextFacade facade) {
    this.facade = facade;
  }

  public Optional<RiskParameterResource> fetchByInfrastructureTypeAndFailureType(String infrastructureType,
      String failureType) {
    return facade.fetchByInfrastructureTypeAndFailureType(infrastructureType, failureType);
  }
}
