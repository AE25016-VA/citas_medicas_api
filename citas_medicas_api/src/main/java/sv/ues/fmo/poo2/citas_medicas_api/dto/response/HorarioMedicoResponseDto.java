package sv.ues.fmo.poo2.citas_medicas_api.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ 
    "idHorario", "medicoId", "nombreCompletoMedico", 
    "diasTurno", "horaInicio", "horaFin", "duracionConsultaMinutos" 
})
public class HorarioMedicoResponseDto {
    private Long idHorario;
    private Long medicoId;
    private String nombreCompletoMedico;
    private String diasTurno;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer duracionConsultaMinutos;
}