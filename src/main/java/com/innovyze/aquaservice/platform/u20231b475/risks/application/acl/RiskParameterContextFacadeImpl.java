package com.innovyze.aquaservice.platform.u20231b475.risks.application.acl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.queries.GetRiskParameterByInfrastructureTypeAndFailureType;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.services.RiskParameterQueryService;
import com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.acl.RiskParameterContextFacade;
import com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.rest.resources.RiskParameterResource;
import com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.rest.transform.RiskParameterResourceFromEntityAssembler;

@Service
public class RiskParameterContextFacadeImpl implements RiskParameterContextFacade {
  private final RiskParameterQueryService service;

  public RiskParameterContextFacadeImpl(RiskParameterQueryService service) {
    this.service = service;
  }

  @Override
  public Optional<RiskParameterResource> fetchByInfrastructureTypeAndFailureType(String infrastructureType,
      String failureType) {
    var query = new GetRiskParameterByInfrastructureTypeAndFailureType(infrastructureType, failureType);
    return service.handle(query)
        .map(RiskParameterResourceFromEntityAssembler::toResourceFromEntity);
  }
}
