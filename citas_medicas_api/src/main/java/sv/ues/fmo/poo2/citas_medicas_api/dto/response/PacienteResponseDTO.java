package sv.ues.fmo.poo2.citas_medicas_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponseDTO {

    private Long idPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String correoPaciente;
    private String telefonoPaciente;
}