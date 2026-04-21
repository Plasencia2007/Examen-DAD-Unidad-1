package com.plasencia.msventas.dto;

import com.plasencia.msventas.validation.annotation.ValidCantidad;
import com.plasencia.msventas.validation.groups.OnCreate;
import com.plasencia.msventas.validation.groups.OnUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleRequestDTO {

    @NotNull(message = "{validation.detalle.producto.notnull}",
             groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 1, groups = {OnCreate.class, OnUpdate.class})
    private Long productoId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    @ValidCantidad(groups = {OnCreate.class, OnUpdate.class})
    private Integer cantidad;
}
