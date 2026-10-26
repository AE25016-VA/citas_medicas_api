package sv.ues.fmo.poo2.citas_medicas_api.dto.response;

import lombok.Data;

@Data
public class MedicoResponseDto {
    private Long idMedico;
    private String nombreMedico;
    private String apellidoMedico;
    private String correoMedico;
    private String nombreUsuario;
    private Long idEspecialidad;
    private String nombreEspecialidad;
}