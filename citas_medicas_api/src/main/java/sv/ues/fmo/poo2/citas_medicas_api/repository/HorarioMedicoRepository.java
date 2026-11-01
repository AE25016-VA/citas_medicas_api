package sv.ues.fmo.poo2.citas_medicas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.ues.fmo.poo2.citas_medicas_api.model.HorarioMedico;

import java.util.List;

public interface HorarioMedicoRepository
        extends JpaRepository<HorarioMedico, Long> {

    List<HorarioMedico> findByMedico_IdMedico(Long idMedico);
}