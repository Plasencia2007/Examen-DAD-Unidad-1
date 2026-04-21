# Microservicio de Clientes (ms-cliente)

Este microservicio se encarga de la gestión de clientes en el sistema.

## Propósito
Manejar el dominio de Clientes de forma independiente, proporcionando operaciones CRUD básicas a través de una API REST.

## Endpoints Disponibles

### Obtener todos los clientes
`GET /api/clientes`

**Ejemplo curl:**
```bash
curl http://localhost:8080/api/clientes
```

### Obtener cliente por ID
`GET /api/clientes/{id}`

**Ejemplo curl:**
```bash
curl http://localhost:8080/api/clientes/1
```

### Crear nuevo cliente
`POST /api/clientes`

**Ejemplo curl:**
```bash
curl -X POST http://localhost:8080/api/clientes \
     -H "Content-Type: application/json" \
     -d '{"nombre":"Luis","apellido":"Diaz","email":"luis.diaz@mail.com","telefono":"987654321","direccion":"Av. Central 456"}'
```

### Actualizar cliente
`PUT /api/clientes/{id}`

**Ejemplo curl:**
```bash
curl -X PUT http://localhost:8080/api/clientes/1 \
     -H "Content-Type: application/json" \
     -d '{"nombre":"Juan Ignacio","apellido":"Pérez","email":"juan.perez@mail.com","telefono":"999111222","direccion":"Av. Siempre Viva 123"}'
```

### Eliminar cliente
`DELETE /api/clientes/{id}`

**Ejemplo curl:**
```bash
curl -X DELETE http://localhost:8080/api/clientes/1
```

## Orden de arranque recomendado
1. **MySQL** - Puerto 3307
2. **Config Server** - Puerto 8888
3. **Eureka Server** - Puerto 8761
4. **ms-cliente** - Puerto 8083 (o registro dinámico)
5. **API Gateway** - Puerto 8080
