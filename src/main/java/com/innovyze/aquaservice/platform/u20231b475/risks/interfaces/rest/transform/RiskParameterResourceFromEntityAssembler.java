package com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.rest.transform;

import com.innovyze.aquaservice.platform.u20231b475.risks.domain.model.aggregates.RiskParameter;
import com.innovyze.aquaservice.platform.u20231b475.risks.interfaces.rest.resources.RiskParameterResource;

/**
 * Assembler to convert a RiskParameter entity to a RiskParameterResource
 * 
 * @author Valentino Solis
 */
public class RiskParameterResourceFromEntityAssembler {
  public static RiskParameterResource toResourceFromEntity(RiskParameter entity) {
    return new RiskParameterResource(
        entity.getInfrastructureType().toString(), entity.getFailureType().toString(), entity.getMinImpactValue(),
        entity.getMaxImpactValue(), entity.getSeverityThreshold());
  }
}
