package com.innovyze.aquaservice.platform.u20231b475.emergency.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.aggregates.EvacuationOrder;

@Repository
public interface EvacuationOrderRepository extends JpaRepository<EvacuationOrder, Long> {
}
