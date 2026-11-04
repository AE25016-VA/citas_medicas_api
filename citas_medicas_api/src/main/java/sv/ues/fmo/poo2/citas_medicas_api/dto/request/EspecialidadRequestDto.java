package sv.ues.fmo.poo2.citas_medicas_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EspecialidadRequestDto {

    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    private String nombreEspecialidad;

    private String descripcionEspecialidad;
}