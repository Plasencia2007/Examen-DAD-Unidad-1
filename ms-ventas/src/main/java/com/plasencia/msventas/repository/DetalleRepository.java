package com.plasencia.msventas.repository;

import com.plasencia.msventas.entity.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleRepository extends JpaRepository<Detalle, Long> {
    List<Detalle> findByVentaId(Long ventaId);
    List<Detalle> findByProductoId(Long productoId);
}
