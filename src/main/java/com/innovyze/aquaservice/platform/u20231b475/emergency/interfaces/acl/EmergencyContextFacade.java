package com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.acl;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Facade interface for managing emergency context operations.
 * 
 * Provides methods to handle emergency-related actions such as creating evacuation orders.
 */
public interface EmergencyContextFacade {
  
  /**
   * Creates an evacuation order for affected zones.
   * 
   * @param assetCode the unique identifier of the asset initiating the evacuation order
   * @param affectedZones a list of zone identifiers that are affected by the evacuation
   * @param reason the reason for issuing the evacuation order
   * @param priority the priority level of the evacuation order
   * @param issuedAt the date and time when the evacuation order was issued
   * @param validUntil the date and time until which the evacuation order is valid
   * @return the unique identifier (ID) of the created evacuation order
   */
  Long createEvacuationOrder(String assetCode, List<String> affectedZones, String reason,
    String priority, LocalDateTime issuedAt, LocalDateTime validUntil);
}
