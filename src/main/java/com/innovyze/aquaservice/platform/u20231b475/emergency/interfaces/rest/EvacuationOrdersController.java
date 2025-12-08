package com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.model.queries.GetAllEvacuationOrdersQuery;
import com.innovyze.aquaservice.platform.u20231b475.emergency.domain.services.EvacuationOrderQueryService;
import com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.rest.resources.EvacuationOrderResource;
import com.innovyze.aquaservice.platform.u20231b475.emergency.interfaces.rest.transform.EvacuationOrderResourceFromEntityAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/api/v1/evacuation-orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Evacuation Orders", description = "Available evacuation orders")
public class EvacuationOrdersController {
  private final EvacuationOrderQueryService service;

  public EvacuationOrdersController(EvacuationOrderQueryService service) {
    this.service = service;
  }

  @GetMapping
  @Operation(summary = "Get all evacuation orders", description = "Get all evacuation orders")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Resources found"),
      @ApiResponse(responseCode = "404", description = "Not found")
  })
  public ResponseEntity<List<EvacuationOrderResource>> getAllEvacuationOrders() {
    var evacuationOrders = service.handle(new GetAllEvacuationOrdersQuery());
    if (evacuationOrders.isEmpty())
      return ResponseEntity.notFound().build();

    var evacuationOrdersResources = evacuationOrders.stream()
        .map(EvacuationOrderResourceFromEntityAssembler::toResourceFromEntity).toList();
    return ResponseEntity.ok(evacuationOrdersResources);
  }
}
