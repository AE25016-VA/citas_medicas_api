package sv.ues.fmo.poo2.citas_medicas_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombrePaciente;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    private String apellidoPaciente;

    @NotBlank(message = "El correo es obligatorio")
    @Email
    @Size(max = 100)
    private String correoPaciente;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100)
    private String contrasenaPaciente;

    @Size(max = 100)
    private String telefonoPaciente;
}