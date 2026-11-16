package sv.ues.fmo.poo2.citas_medicas_api.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ 
    "idCita", "pacienteId", "nombrePaciente", "medicoId", 
    "nombreMedico", "especialidadId", "nombreEspecialidad", 
    "fechaCita", "horaCita", "horaFinCita", "motivoConsulta", "estadoCita" 
})
public class CitaResponseDTO {
    private Long idCita;
    private Long pacienteId;
    private String nombrePaciente;
    private Long medicoId;
    private String nombreMedico;
    private Long especialidadId;
    private String nombreEspecialidad;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private LocalTime horaFinCita;
    private String motivoConsulta;
    private String estadoCita;
}