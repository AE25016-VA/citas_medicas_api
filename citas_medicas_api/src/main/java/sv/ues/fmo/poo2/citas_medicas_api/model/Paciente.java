package sv.ues.fmo.poo2.citas_medicas_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente") 
    private Long idPaciente;

    @Column(name = "nombre_paciente", nullable = false, length = 100)
    private String nombrePaciente;

    @Column(name = "apellido_paciente", nullable = false, length = 100)
    private String apellidoPaciente;

    @Column(name = "correo_paciente", nullable = false, unique = true, length = 100)
    private String correoPaciente;

    @Column(name = "contraseña_paciente", nullable = false, length = 100)
    private String contrasenaPaciente;

    @Column(name = "telefono_paciente", length = 100)
    private String telefonoPaciente;
}
