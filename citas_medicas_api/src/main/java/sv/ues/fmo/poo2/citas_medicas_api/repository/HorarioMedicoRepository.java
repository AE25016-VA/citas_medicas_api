package sv.ues.fmo.poo2.citas_medicas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.ues.fmo.poo2.citas_medicas_api.model.HorarioMedico;

import java.util.List;

@Repository
public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Long> {
    List<HorarioMedico> findByMedicoIdMedico(Long medicoId);
}