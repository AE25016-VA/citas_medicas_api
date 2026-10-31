package sv.ues.fmo.poo2.citas_medicas_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "nombre_medico", nullable = false, length = 50)
    private String nombreMedico;

    @Column(name = "apellido_medico", nullable = false, length = 50)
    private String apellidoMedico;

    @Column(name = "correo_medico", nullable = false, unique = true, length = 50)
    private String correoMedico;

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Column(name = "contraseña", nullable = false, length = 50)
    private String contrasena;

    @Column(name = "genero_medico", nullable = false, length = 20)
    private String generoMedico;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "especialidad_medico",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private Set<Especialidad> especialidades = new HashSet<>();
}