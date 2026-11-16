package sv.ues.fmo.poo2.citas_medicas_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.CitaRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.CitaResponseDTO;
import sv.ues.fmo.poo2.citas_medicas_api.model.*;
import sv.ues.fmo.poo2.citas_medicas_api.repository.*;
import sv.ues.fmo.poo2.citas_medicas_api.service.CitaService;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;
    private final HorarioMedicoRepository horarioMedicoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> listarCitas() {
        return citaRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponseDTO obtenerCitaPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        return mapearADTO(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> obtenerCitasPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteIdPaciente(pacienteId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> obtenerCitasPorMedico(Long medicoId) {
        return citaRepository.findByMedicoIdMedico(medicoId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CitaResponseDTO crearCita(CitaRequestDTO requestDTO) {
        return procesarYGuardarCita(null, requestDTO);
    }

    @Override
    @Transactional
    public CitaResponseDTO actualizarCita(Long id, CitaRequestDTO requestDTO) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        
        return procesarYGuardarCita(id, requestDTO);

        
    }
    

    @Override
    @Transactional
    public void cancelarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        cita.setEstadoCita("CANCELADA");
        citaRepository.save(cita);
    }

    private CitaResponseDTO procesarYGuardarCita(Long citaId, CitaRequestDTO requestDTO) {
        Paciente paciente = pacienteRepository.findById(requestDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + requestDTO.getPacienteId()));

        Medico medico = medicoRepository.findById(requestDTO.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + requestDTO.getMedicoId()));

        Especialidad especialidad = especialidadRepository.findById(requestDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + requestDTO.getEspecialidadId()));

        // 1. VALIDACIÓN: Especialidad del médico
        boolean especialidadValida = medico.getEspecialidades().stream()
                .anyMatch(e -> e.getIdEspecialidad().equals(especialidad.getIdEspecialidad()));

        if (!especialidadValida) {
            throw new IllegalArgumentException("El médico seleccionado no imparte la especialidad de " + especialidad.getNombreEspecialidad());
        }

        // 2. VALIDACIÓN: Horario de atención del médico
        String diaSemana = traducirDia(requestDTO.getFechaCita().getDayOfWeek().name());
        List<HorarioMedico> horariosMedico = horarioMedicoRepository.findByMedicoIdMedico(medico.getIdMedico());

        HorarioMedico turnoCorrespondiente = horariosMedico.stream()
                .filter(h -> h.getDiasTurno().toUpperCase().contains(diaSemana))
                .filter(h -> !requestDTO.getHoraCita().isBefore(h.getHoraInicio()) && requestDTO.getHoraCita().isBefore(h.getHoraFin()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El médico no atiende el día " + diaSemana + " a las " + requestDTO.getHoraCita()));

        // Calcular hora fin según duracionConsultaMinutos (ej. 30 min)
        LocalTime horaFinCita = requestDTO.getHoraCita().plusMinutes(turnoCorrespondiente.getDuracionConsultaMinutos());

        if (horaFinCita.isAfter(turnoCorrespondiente.getHoraFin())) {
            throw new IllegalArgumentException("La consulta excede el horario límite del médico (" + turnoCorrespondiente.getHoraFin() + ")");
        }

        // 3. VALIDACIÓN: Solapamiento con otras citas activas
        boolean existeSolapamiento = citaRepository.existeSolapamientoCita(
                medico.getIdMedico(),
                requestDTO.getFechaCita(),
                requestDTO.getHoraCita(),
                horaFinCita,
                citaId
        );

        if (existeSolapamiento) {
            throw new IllegalArgumentException("El médico ya tiene una cita reservada entre " + requestDTO.getHoraCita() + " y " + horaFinCita);
        }

        // Construir la entidad usando Builder
        Cita cita;
        if (citaId == null) {
            cita = Cita.builder()
                    .paciente(paciente)
                    .medico(medico)
                    .especialidad(especialidad)
                    .fechaCita(requestDTO.getFechaCita())
                    .horaCita(requestDTO.getHoraCita())
                    .horaFinCita(horaFinCita)
                    .motivoConsulta(requestDTO.getMotivoConsulta())
                    .estadoCita("CONFIRMADA")
                    .build();
        } else {
            cita = citaRepository.findById(citaId).get();
            cita.setPaciente(paciente);
            cita.setMedico(medico);
            cita.setEspecialidad(especialidad);
            cita.setFechaCita(requestDTO.getFechaCita());
            cita.setHoraCita(requestDTO.getHoraCita());
            cita.setHoraFinCita(horaFinCita);
            cita.setMotivoConsulta(requestDTO.getMotivoConsulta());
            if (requestDTO.getEstadoCita() != null && !requestDTO.getEstadoCita().isBlank()) {
        cita.setEstadoCita(requestDTO.getEstadoCita().toUpperCase());
    }
        }

        return mapearADTO(citaRepository.save(cita));
    }

    private String traducirDia(String dayOfWeekName) {
        switch (dayOfWeekName) {
            case "MONDAY": return "LUNES";
            case "TUESDAY": return "MARTES";
            case "WEDNESDAY": return "MIERCOLES";
            case "THURSDAY": return "JUEVES";
            case "FRIDAY": return "VIERNES";
            case "SATURDAY": return "SABADO";
            case "SUNDAY": return "DOMINGO";
            default: return "";
        }
    }

    private CitaResponseDTO mapearADTO(Cita cita) {
        return CitaResponseDTO.builder()
                .idCita(cita.getIdCita())
                .pacienteId(cita.getPaciente().getIdPaciente())
                .nombrePaciente(cita.getPaciente().getNombrePaciente() + " " + cita.getPaciente().getApellidoPaciente())
                .medicoId(cita.getMedico().getIdMedico())
                .nombreMedico(cita.getMedico().getNombreMedico() + " " + cita.getMedico().getApellidoMedico())
                .especialidadId(cita.getEspecialidad().getIdEspecialidad())
                .nombreEspecialidad(cita.getEspecialidad().getNombreEspecialidad())
                .fechaCita(cita.getFechaCita())
                .horaCita(cita.getHoraCita())
                .horaFinCita(cita.getHoraFinCita())
                .motivoConsulta(cita.getMotivoConsulta())
                .estadoCita(cita.getEstadoCita())
                .build();
    }
}
