package com.plasencia.msproducto.dto;

import com.plasencia.msproducto.validation.annotation.UniqueNombreCategoria;
import com.plasencia.msproducto.validation.groups.OnCreate;
import com.plasencia.msproducto.validation.groups.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {

    @NotBlank(message = "{validation.categoria.nombre.notblank}",
              groups = { OnCreate.class, OnUpdate.class })
    @Size(max = 100, groups = { OnCreate.class, OnUpdate.class })
    @UniqueNombreCategoria(groups = OnCreate.class)
    private String nombre;

    @Size(max = 250, groups = { OnCreate.class, OnUpdate.class })
    private String descripcion;

    private Boolean activo;
}
