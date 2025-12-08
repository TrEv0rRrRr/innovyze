package com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovyze.aquaservice.platform.u20231b475.incidents.domain.services.FailureReportCommandService;
import com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.resources.CreateFailureReportResource;
import com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.resources.FailureReportResource;
import com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.transform.CreateFailureReportCommandFromResourceAssembler;
import com.innovyze.aquaservice.platform.u20231b475.incidents.interfaces.rest.transform.FailureReportResourceFromEntityAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/failure-reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Failure Reports", description = "Available failure reports")
public class FailureReportsController {
  private final FailureReportCommandService service;

  public FailureReportsController(FailureReportCommandService service) {
    this.service = service;
  }

  @PostMapping
  @Operation(summary = "Creates a new failure report")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Failure report created"),
      @ApiResponse(responseCode = "400", description = "Bad request")
  })
  public ResponseEntity<FailureReportResource> createFailureReport(
      @Valid @RequestBody CreateFailureReportResource resource) {
    var createFailureReportCommand = CreateFailureReportCommandFromResourceAssembler.toCommandFromResource(resource);
    var failureReport = service.handle(createFailureReportCommand);

    if (failureReport.isEmpty())
      return ResponseEntity.notFound().build();

    var createFailureReport = failureReport.get();

    var failureReportResource = FailureReportResourceFromEntityAssembler.toResourceFromEntity(createFailureReport);

    return new ResponseEntity<>(failureReportResource, HttpStatus.CREATED);
  }
}
