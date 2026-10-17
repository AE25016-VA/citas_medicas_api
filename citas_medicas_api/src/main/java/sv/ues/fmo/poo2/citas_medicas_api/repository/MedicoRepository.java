package sv.ues.fmo.poo2.citas_medicas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.ues.fmo.poo2.citas_medicas_api.model.Medico;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByCorreoMedico(String correoMedico);
    Optional<Medico> findByNombreUsuario(String nombreUsuario);
}