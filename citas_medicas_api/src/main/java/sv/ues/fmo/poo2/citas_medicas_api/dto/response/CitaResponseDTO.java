package sv.ues.fmo.poo2.citas_medicas_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaResponseDTO {
    private Long idCita;
    private String nombrePaciente;
    private String nombreMedico;
    private String nombreEspecialidad;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String estadoCita;
    private String motivoCita;
}
