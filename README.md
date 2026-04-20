# Infraestructura de Microservicios - Examen Unidad 1

Este proyecto contiene la infraestructura base para un sistema de microservicios utilizando Spring Cloud 2025.0.x.

## Orden de Arranque

Es fundamental seguir este orden para que los servicios se registren y configuren correctamente:

1.  **Config Server (Puerto 8888):** Provee la configuración centralizada.
2.  **Eureka Server (Puerto 8761):** Servidor de descubrimiento.
3.  **API Gateway (Puerto 8080):** Puerta de enlace y enrutador.
4.  **Microservicios de Negocio:** (alumno-service, escuela-service, etc.) - Se agregarán después.

## Comandos para arrancar (Maven)

Desde la raíz de cada carpeta:
```bash
mvn spring-boot:run
```

## Verificación

| Servicio | URL de Verificación |
| :--- | :--- |
| **Config Server** | [http://localhost:8888/eureka-server/default](http://localhost:8888/eureka-server/default) |
| **Eureka Server** | [http://localhost:8761](http://localhost:8761) |
| **API Gateway (Actuator)** | [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) |

## Configuración Detallada

- **Config Server:** Configurado en modo `native` con búsqueda en `src/main/resources/config/`.
- **API Gateway:** Utiliza la pila reactiva (`webflux`) y tiene configuradas rutas dinámicas vía Eureka.
- **Actuator:** Todos los servicios tienen expuestos los endpoints de `health` e `info`. El gateway también expone `gateway` y el config-server `refresh`.
