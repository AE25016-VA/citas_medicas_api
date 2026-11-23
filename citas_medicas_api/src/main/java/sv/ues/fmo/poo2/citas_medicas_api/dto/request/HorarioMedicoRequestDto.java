package sv.ues.fmo.poo2.citas_medicas_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioMedicoRequestDto {

    @NotNull(message = "El ID del médico es obligatorio")
    private Long medicoId;

    @NotBlank(message = "Los días de turno son obligatorios")
    private String diasTurno;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "La duración de la consulta es obligatoria")
    @Min(value = 10, message = "La duración mínima debe ser de al menos 10 minutos")
    private Integer duracionConsultaMinutos;
}