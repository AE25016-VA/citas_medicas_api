package sv.ues.fmo.poo2.citas_medicas_api.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.ues.fmo.poo2.citas_medicas_api.model.Paciente;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    boolean existsByCorreoPaciente(String correoPaciente);

    Optional<Paciente> findByCorreoPaciente(String correoPaciente);
}