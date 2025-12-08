package com.innovyze.aquaservice.platform.u20231b475.incidents.application.internal.queryservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.aggregates.FailureReport;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.queries.GetFailureReportByAssetCodeAndFailureTypeAndReportedAt;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.services.FailureReportQueryService;
import com.innovyze.aquaservice.platform.u20231b475.incidents.infrastructure.persistence.jpa.repositories.FailureReportRepository;

@Service
public class FailureReportQueryServiceImpl implements FailureReportQueryService {
  private final FailureReportRepository repo;

  public FailureReportQueryServiceImpl(FailureReportRepository _repo) {
    this.repo = _repo;
  }

  @Override
  public Optional<FailureReport> handle(GetFailureReportByAssetCodeAndFailureTypeAndReportedAt query) {
    return repo.findByAssetCodeAndFailureTypeAndReportedAt(query.assetCode(), query.failureType(), query.reportedAt());
  }
}
