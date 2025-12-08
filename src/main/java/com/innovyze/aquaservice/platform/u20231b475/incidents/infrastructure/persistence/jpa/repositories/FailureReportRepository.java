package com.innovyze.aquaservice.platform.u20231b475.incidents.infrastructure.persistence.jpa.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innovyze.aquaservice.platform.u20231b475.incidents.application.internal.eventhandlers.IncidentEvaluatedEventHandler;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.aggregates.FailureReport;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.AssetCode;
import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.model.valueobjects.FailureReportState;

@Repository
public interface FailureReportRepository extends JpaRepository<FailureReport, Long> {
        Optional<FailureReport> findByAssetCodeAndFailureTypeAndReportedAt(AssetCode assetCode, String failureType,
                        LocalDateTime reportedAt);

        boolean existsByAssetCodeAndFailureTypeAndReportedAt(AssetCode assetCode, String failureType,
                        LocalDateTime reportedAt);

        /**
         * This query is to update the state of a failure report in the
         * {@link IncidentEvaluatedEventHandler} event.
         */
        @Modifying
        @Query("UPDATE FailureReport f SET f.state = :state WHERE f.id = :id")
        void updateStateById(@Param("id") Long id, @Param("state") FailureReportState state);
}
