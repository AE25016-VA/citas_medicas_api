package sv.ues.fmo.poo2.citas_medicas_api.dto.response;

import lombok.Data;

@Data
public class EspecialidadResponseDto {
    private Long idEspecialidad;
    private String nombreEspecialidad;
    private String descripcionEspecialidad;
}