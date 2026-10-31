package sv.ues.fmo.poo2.citas_medicas_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class MedicoRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreMedico;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidoMedico;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correoMedico;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @NotBlank(message = "El género es obligatorio")
    private String generoMedico;

    private Set<Long> especialidadesIds;
}