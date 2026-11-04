package sv.ues.fmo.poo2.citas_medicas_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalTime;

@Data
public class HorarioMedicoRequestDTO {

    @NotNull
    @Positive
    private Long idMedico;

    @NotBlank
    @Size(max = 50)
    private String diasTurno;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;
}