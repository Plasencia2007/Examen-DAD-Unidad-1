# ms-ventas — Microservicio Agregador de Ventas

Microservicio responsable de gestionar ventas. Es el **único agregador** del sistema:
consume `ms-cliente` y `ms-producto` vía OpenFeign con Circuit Breaker (Resilience4J).

```
          ┌─────────────┐
          │  ms-ventas  │  :8082
          └──────┬──────┘
                 │ Feign
        ┌────────┴────────┐
        ▼                 ▼
  ┌───────────┐    ┌─────────────┐
  │ ms-cliente│    │ ms-producto │
  │   :8083   │    │   :8081     │
  └───────────┘    └─────────────┘
```

## Endpoints

| Método | Ruta                        | Descripción                        |
|--------|-----------------------------|------------------------------------|
| GET    | /api/ventas                 | Listar todas las ventas            |
| GET    | /api/ventas/{id}            | Obtener venta por ID (enriquecida) |
| GET    | /api/ventas/cliente/{id}    | Ventas por cliente                 |
| GET    | /api/ventas/estado/{estado} | Ventas por estado (PENDIENTE, etc) |
| POST   | /api/ventas                 | Crear nueva venta                  |
| POST   | /api/ventas/{id}/confirmar  | Confirmar venta PENDIENTE          |
| POST   | /api/ventas/{id}/anular     | Anular venta (devuelve stock)      |

## Orden de arranque

1. `eureka-server` (puerto 8761)
2. `config-server` (puerto 8888)
3. `ms-cliente`, `ms-producto` (en cualquier orden)
4. `ms-ventas` (puerto 8082)
5. `api-gateway` (puerto 8080)

## Ejemplo — Crear una venta

```bash
curl -X POST http://localhost:8080/api/ventas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "fecha": "2026-04-20T10:00:00",
    "detalles": [
      { "productoId": 1, "cantidad": 1 },
      { "productoId": 2, "cantidad": 2 }
    ]
  }'
```

## Ejemplos adicionales

```bash
# Venta con stock insuficiente → 409 CONFLICT
curl -X POST http://localhost:8080/api/ventas \
  -H "Content-Type: application/json" \
  -d '{"clienteId":1,"fecha":"2026-04-20T10:00:00","detalles":[{"productoId":1,"cantidad":99999}]}'

# Consultar venta enriquecida (con datos de cliente y productos)
curl http://localhost:8080/api/ventas/1

# Anular venta (devuelve stock a ms-producto)
curl -X POST http://localhost:8080/api/ventas/1/anular
```

## Probar el Circuit Breaker

1. Apagar `ms-cliente`.
2. Llamar `GET /api/ventas/1`.
3. La respuesta sigue llegando con `clienteNombreCompleto: "CLIENTE NO DISPONIBLE"` en lugar de un error 500.
4. El circuit breaker se abre tras 5 fallos consecutivos (50% de `sliding-window-size: 10`).
5. Después de `wait-duration-in-open-state: 10s` pasa a HALF-OPEN y permite 3 llamadas de prueba.
