package com.plasencia.msventas.repository;

import com.plasencia.msventas.entity.EstadoVenta;
import com.plasencia.msventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByClienteId(Long clienteId);
    List<Venta> findByEstado(EstadoVenta estado);
    List<Venta> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
