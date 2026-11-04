package sv.ues.fmo.poo2.citas_medicas_api.dto.request;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitaRequestDTO {
    private Long pacienteId;
    private Long medicoId;
    private Long especialidadId;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String motivoCita;
}