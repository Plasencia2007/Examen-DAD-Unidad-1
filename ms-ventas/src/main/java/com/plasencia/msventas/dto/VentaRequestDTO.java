package com.plasencia.msventas.dto;

import com.plasencia.msventas.validation.annotation.DetallesNoVacios;
import com.plasencia.msventas.validation.annotation.ValidFechaVenta;
import com.plasencia.msventas.validation.groups.OnCreate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {

    @NotNull(message = "{validation.venta.cliente.notnull}", groups = OnCreate.class)
    @Min(value = 1, groups = OnCreate.class)
    private Long clienteId;

    @NotNull(groups = OnCreate.class)
    @ValidFechaVenta(groups = OnCreate.class)
    private LocalDateTime fecha;

    @NotNull(groups = OnCreate.class)
    @DetallesNoVacios(groups = OnCreate.class)
    @Valid
    private List<DetalleRequestDTO> detalles;
}
