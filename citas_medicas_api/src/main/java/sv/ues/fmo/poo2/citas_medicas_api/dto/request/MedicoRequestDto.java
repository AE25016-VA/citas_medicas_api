package sv.ues.fmo.poo2.citas_medicas_api.dto.request;

import lombok.Data;

@Data
public class MedicoRequestDto {
    private String nombreMedico;
    private String apellidoMedico;
    private String correoMedico;
    private String nombreUsuario;
    private String contrasena;
    private String generoMedico;
    private Long idEspecialidad;
}