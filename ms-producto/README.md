# Microservicio de Productos (ms-producto)

Este microservicio se encarga de la gestión de categorías y productos para el sistema.

## Tecnologías
- Spring Boot 3.5.x
- Spring Cloud 2025.0.x
- Spring Data JPA
- Oracle Free 23
- Lombok
- Jakarta Validation

## Endpoints Principales

### Categorías
- `GET /api/categorias`: Listar todas.
- `GET /api/categorias/activas`: Solo activas.
- `POST /api/categorias`: Crear (Validación OnCreate).
- `PUT /api/categorias/{id}`: Actualizar (Validación OnUpdate).
- `DELETE /api/categorias/{id}`: Soft delete.

### Productos
- `GET /api/productos`: Listar todos.
- `GET /api/productos/activos`: Solo activos.
- `GET /api/productos/categoria/{id}`: Filtrar por categoría.
- `POST /api/productos`: Crear.
- `PUT /api/productos/{id}`: Actualizar.
- `PATCH /api/productos/{id}/stock?delta=n`: Ajustar stock.
- `DELETE /api/productos/{id}`: Soft delete.

## Ejemplos de Payloads y Validaciones

### 1. Crear Categoría Válida
```bash
curl -X POST http://localhost:8081/api/categorias \
-H "Content-Type: application/json" \
-d '{"nombre": "Hogar", "descripcion": "Artículos para el hogar"}'
```

### 2. Nombre Duplicado (Falla 409)
```bash
curl -X POST http://localhost:8081/api/categorias \
-H "Content-Type: application/json" \
-d '{"nombre": "Electrónica"}'
```

### 3. Producto con Categoría Inexistente (Falla 400)
```bash
curl -X POST http://localhost:8081/api/productos \
-H "Content-Type: application/json" \
-d '{"nombre": "Teclado", "precio": 50.00, "stock": 10, "categoriaId": 999}'
```

### 4. Producto con Precio Negativo (Falla 400)
```bash
curl -X POST http://localhost:8081/api/productos \
-H "Content-Type: application/json" \
-d '{"nombre": "Error", "precio": -10, "stock": 5, "categoriaId": 1}'
```

## ¿Por qué BigDecimal para el precio?
Usamos `BigDecimal` en lugar de `double` o `float` porque los tipos de punto flotante binarios no pueden representar con precisión fracciones decimales como 0.1 o 0.2, lo que lleva a errores de redondeo acumulativos en cálculos financieros. `BigDecimal` permite un control total sobre la escala y el redondeo, siendo el estándar para aplicaciones bancarias y de comercio.
