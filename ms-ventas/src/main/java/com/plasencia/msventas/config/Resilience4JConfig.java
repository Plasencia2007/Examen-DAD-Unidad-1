package com.plasencia.msventas.config;

import org.springframework.context.annotation.Configuration;

/**
 * Los circuit breakers para ms-cliente y ms-producto se configuran en application.yml
 * bajo resilience4j.circuitbreaker.instances.*
 * Feign los activa automáticamente con spring.cloud.openfeign.circuitbreaker.enabled=true
 */
@Configuration
public class Resilience4JConfig {
}
