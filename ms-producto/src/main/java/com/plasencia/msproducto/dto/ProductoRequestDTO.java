package com.plasencia.msproducto.dto;

import com.plasencia.msproducto.validation.annotation.CategoriaExiste;
import com.plasencia.msproducto.validation.annotation.ValidPrecio;
import com.plasencia.msproducto.validation.annotation.ValidStock;
import com.plasencia.msproducto.validation.groups.OnCreate;
import com.plasencia.msproducto.validation.groups.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "{validation.producto.nombre.notblank}",
              groups = { OnCreate.class, OnUpdate.class })
    @Size(max = 150, groups = { OnCreate.class, OnUpdate.class })
    private String nombre;

    @Size(max = 500, groups = { OnCreate.class, OnUpdate.class })
    private String descripcion;

    @NotNull(message = "{validation.producto.precio.notnull}",
             groups = { OnCreate.class, OnUpdate.class })
    @ValidPrecio(groups = { OnCreate.class, OnUpdate.class })
    private BigDecimal precio;

    @NotNull(groups = { OnCreate.class, OnUpdate.class })
    @ValidStock(groups = { OnCreate.class, OnUpdate.class })
    private Integer stock;

    private Boolean activo;

    @NotNull(message = "{validation.producto.categoria.notnull}",
             groups = { OnCreate.class, OnUpdate.class })
    @CategoriaExiste(groups = { OnCreate.class, OnUpdate.class })
    private Long categoriaId;
}
