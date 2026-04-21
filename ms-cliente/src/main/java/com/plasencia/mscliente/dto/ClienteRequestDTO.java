package com.plasencia.mscliente.dto;

import com.plasencia.mscliente.validation.annotation.UniqueEmail;
import com.plasencia.mscliente.validation.annotation.ValidNombreApellido;
import com.plasencia.mscliente.validation.annotation.ValidTelefono;
import com.plasencia.mscliente.validation.groups.OnCreate;
import com.plasencia.mscliente.validation.groups.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    @NotBlank(message = "{validation.cliente.nombre.notblank}",
              groups = { OnCreate.class, OnUpdate.class })
    @Size(max = 100, message = "{validation.cliente.nombre.size}",
          groups = { OnCreate.class, OnUpdate.class })
    @ValidNombreApellido(groups = { OnCreate.class, OnUpdate.class })
    private String nombre;

    @NotBlank(message = "{validation.cliente.apellido.notblank}",
              groups = { OnCreate.class, OnUpdate.class })
    @Size(max = 100, groups = { OnCreate.class, OnUpdate.class })
    @ValidNombreApellido(groups = { OnCreate.class, OnUpdate.class })
    private String apellido;

    @NotBlank(message = "{validation.cliente.email.notblank}",
              groups = { OnCreate.class, OnUpdate.class })
    @Email(message = "{validation.cliente.email.formato}",
           groups = { OnCreate.class, OnUpdate.class })
    @Size(max = 150, groups = { OnCreate.class, OnUpdate.class })
    @UniqueEmail(groups = OnCreate.class) // Solo al crear
    private String email;

    @ValidTelefono(groups = { OnCreate.class, OnUpdate.class })
    @Size(max = 20, groups = { OnCreate.class, OnUpdate.class })
    private String telefono;

    @Size(max = 250, groups = { OnCreate.class, OnUpdate.class })
    private String direccion;
}
