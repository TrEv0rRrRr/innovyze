package com.innovyze.aquaservice.platform.u20231b475.risks.application.internal.queryservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.aggregates.RiskParameter;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.queries.GetRiskParameterByInfrastructureTypeAndFailureType;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.valueobjects.FailureType;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.valueobjects.InfrastructureType;
import com.innovyze.aquaservice.platform.u20231b475.risks.domain.services.RiskParameterQueryService;
import com.innovyze.aquaservice.platform.u20231b475.risks.infrastructure.persistence.jpa.repositories.RiskParameterRepository;

@Service
public class RiskParameterQueryServiceImpl implements RiskParameterQueryService {
  public final RiskParameterRepository repo;

  public RiskParameterQueryServiceImpl(RiskParameterRepository repo) {
    this.repo = repo;
  }

  @Override
  public Optional<RiskParameter> handle(GetRiskParameterByInfrastructureTypeAndFailureType query) {
    var infrastructureType = InfrastructureType.valueOf(query.infrastructureType());
    var failureType = FailureType.valueOf(query.failureType());
    return repo.findByInfrastructureTypeAndFailureType(infrastructureType, failureType);
  }
}
