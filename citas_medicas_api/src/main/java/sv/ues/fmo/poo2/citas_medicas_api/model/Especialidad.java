package sv.ues.fmo.poo2.citas_medicas_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "especialidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Long idEspecialidad;

    @Column(name = "nombre_especialidad", nullable = false, length = 100)
    private String nombreEspecialidad;

    @Column(name = "descripcion_especialidad", columnDefinition = "TEXT")
    private String descripcionEspecialidad;
}
