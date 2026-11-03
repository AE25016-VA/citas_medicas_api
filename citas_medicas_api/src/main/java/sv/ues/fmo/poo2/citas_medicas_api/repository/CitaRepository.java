package sv.ues.fmo.poo2.citas_medicas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.ues.fmo.poo2.citas_medicas_api.model.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
