package sv.ues.fmo.poo2.citas_medicas_api.dto.response;

import lombok.Data;
import java.time.LocalTime;

@Data
public class HorarioMedicoResponseDTO {

    private Long idHorario;
    private Long idMedico;
    private String diasTurno;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}