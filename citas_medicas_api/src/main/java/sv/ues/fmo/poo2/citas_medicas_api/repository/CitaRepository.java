package sv.ues.fmo.poo2.citas_medicas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.ues.fmo.poo2.citas_medicas_api.model.Cita;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByPacienteIdPaciente(Long pacienteId);
    List<Cita> findByMedicoIdMedico(Long medicoId);

    @Query("SELECT COUNT(c) > 0 FROM Cita c WHERE c.medico.idMedico = :medicoId " +
       "AND c.fechaCita = :fecha " +
       "AND c.estadoCita != 'CANCELADA' " +
       "AND (:idCita IS NULL OR c.idCita != :idCita) " +
       "AND ((:horaInicio >= c.horaCita AND :horaInicio < c.horaFinCita) " +
       "OR (:horaFin > c.horaCita AND :horaFin <= c.horaFinCita) " +
       "OR (c.horaCita >= :horaInicio AND c.horaCita < :horaFin))") 
boolean existeSolapamientoCita(
    @Param("medicoId") Long medicoId,
    @Param("fecha") LocalDate fecha,
    @Param("horaInicio") LocalTime horaInicio,
    @Param("horaFin") LocalTime horaFin,
    @Param("idCita") Long idCita
);
}