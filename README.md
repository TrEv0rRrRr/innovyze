# Bounded Contexts

## Nota

Los id auto generados se heredan del `AuditableAbstractAggregateRoot` de la carpeta `shared`.

## FailureReport

- Bounded Context: **incidents**
- Auditables, por ende tienen los atributos `createdAt` y `updatedAt`

### Atributos:

- `id` (Long, Primary Key, Autogenerado)
- `assetCode` (identificador del negocio, embedded type obligatorio, no vacío, solo puede contener un valor UUID válido)
- `infrastructureType` (String, obligatorio, no vacío)
- `failureType` (String, obligatorio, no vacío)
- `reportedImpactValue` (Double, obligatorio)
- `state` (FailureReportState enumeration, obligatorio, no nulo, por defecto PENDING)
- `reportedAt` (LocalDateTime, obligatorio, no nulo)
- `resolvedAt` (LocalDateTime, opcional, puede ser nulo, pero si está presente no debe ser anterior a reportedAt).

### Nota

Especifica que al momento de registrar un failure report, si el reportedImpactValue está fuera del rango entre `minImpactValue` y `maxImpactValue` o excede el `severityThreshold` definido para el `infrastructureType` y `failureType` correspondiente en RiskParameters, el failure report se registra con state `FLAGGED`, y se debe emitir un evento `IncidentEvaluatedEvent`. En caso contrario, el state es `ACCEPTED`.

## RiskParameter

- Bounded Context: **risks**
- No auditable

### Atributos:

- `id` (Long, Primary Key, Autogenerado)
- `infrastructureType` (String, obligatorio, no vacío),
- `failureType` (String, obligatorio, no vacío)
- `minImpactValue` (Double, obligatorio)
- `maxImpactValue` (Double, obligatorio)
- `severityThreshold` (Double, obligatorio)

### Nota

Requiere que su información sea poblada en la base de datos de forma automática asociada al ApplicationReady event. [x]

| `id` | `infrastructureType` | `failureType`      | `minImpactValue` | `maxImpactValue` | `severityThreshold` |
| ---- | -------------------- | ------------------ | ---------------- | ---------------- | ------------------- |
| 1    | WATER_PIPE           | burst              | 0.0              | 100.0            | 75.0                |
| 2    | SEWER_LINE           | overflow           | 0.0              | 50.0             | 40.0                |
| 3    | PUMPING_STATION      | mechanical_failure | 0.0              | 10.0             | 8.0                 |
| 4    | RESERVOIR            | structural_crack   | 0.0              | 200.0            | 150.0               |

## EvacuationOrder

- Bounded Context: **emergency**
- Auditables, por ende tienen los atributos `createdAt` y `updatedAt`

### Atributos:

- `id` (Long, Primary Key, autogenerado)
- `assetCode` (identificador del negocio, embedded type obligatorio, no vacío, solo puede contener un valor UUID válido)
- `affectedZones` (List<String>, obligatorio, no vacía)
- `reason` (String, obligatorio, no vacío)
- `priority` (EvacuationOrderPriority enumeration, obligatorio, no nulo)
- `issuedAt` (LocalDateTime, obligatorio, no nulo)
- `state` (EvacuationOrderState enumeration, obligatorio, no nulo, por defecto ISSUED)
- `validUntil` (LocalDateTime, obligatorio, no nulo, debe ser posterior a issuedAt)

# Business Rules

- Especifica que el atributo `assetCode` debe ser embedded y solo puede contener un valor UUID válido, que debe proporcionarse (no autogenerarse) al momento de registrar un failure report. [x]
- Especifica que `assetCode` se considera un identificador interno del negocio. [x]
- **No** se debe registrar en la base de datos dos failure reports con el mismo valor de `assetCode` y `failureType` **en el mismo día**. [x]
- Requiere que el valor de `reportedAt` no sea mayor que la fecha y hora actual. [x]
- Requiere que el valor de `issuedAt` no sea mayor que la fecha y hora actual, y `validUntil` debe ser al menos 24 horas posterior a issuedAt. [x]

# Domain Events

## IncidentEvaluatedEvent

El Event Handler debe actualizar el state del FailureReport a `EVALUATED` si:

- El `reportedImpactValue` **excede** el `severityThreshold` por **más del 15%**, se debe generar una `EvacuationOrder` para el `assetCode`, con priority `URGENT`, `issuedAt` **igual a la fecha y hora actual**, `validUntil` **igual a `issuedAt` + 72 horas, state `ISSUED`,** y `affectedZones` calculadas en base a un servicio externo (simulado por una lógica simple, por ejemplo, "Zone A", "Zone B", "Zone C" si el impacto es muy alto, "Zone A" si el impacto es moderado). El `reason` debe ser una descripción concisa del tipo de fallo y el impacto.
- Si el `reportedImpactValue` **excede** el `severityThreshold` por **menos del 15%, pero más del 5%,** la priority es `HIGH`, `validUntil` **es issuedAt + 48 horas**. En cualquier otro caso de exceder el umbral, la priority es `MEDIUM`, `validUntil` es **issuedAt + 24 horas**.

## Casos de prueba

### URGENT - Excede >15% el threshold

```json
{
  "assetCode": "550e8400-e29b-41d4-a716-446655440000",
  "infrastructureType": "WATER_PIPE",
  "failureType": "burst",
  "reportedImpactValue": 90.0,
  "reportedAt": "2025-12-08T19:00:00.000Z"
}
```

### HIGH - Excede 5-15% el threshold

```json
{
  "assetCode": "660e8400-e29b-41d4-a716-446655440001",
  "infrastructureType": "RESERVOIR",
  "failureType": "structural_crack",
  "reportedImpactValue": 165.0,
  "reportedAt": "2025-12-08T19:00:00.000Z"
}
```

### MEDIUM - Excede <5% el threshold

```json
{
  "assetCode": "770e8400-e29b-41d4-a716-446655440002",
  "infrastructureType": "SEWER_LINE",
  "failureType": "overflow",
  "reportedImpactValue": 41.5,
  "reportedAt": "2025-12-08T19:00:00.000Z"
}
```

### No genera EvacuationOrder

```json
{
  "assetCode": "880e8400-e29b-41d4-a716-446655440003",
  "infrastructureType": "PUMPING_STATION",
  "failureType": "mechanical_failure",
  "reportedImpactValue": 7.5,
  "reportedAt": "2025-12-08T19:00:00.000Z"
}
```

# Endpoints

## Failure Reports Endpoint `(/api/v1/failure-reports)`

- Implementar una operación `POST`
- El id es autogenerado
- `assetCode` se proporciona como String y debe convertirse a un UUID válido
- Debe retornar un `201`
- Incluir en la response el id generado y los demás atributos, menos los de auditoría.

## Evacuation Orders Endpoint `(/api/v1/evacuation-orders)`

- Implementar una operación `GET`
- Retornar un `200`
- Incluir en la response todos los atributos, hasta los de auditoría.

# ERROR
