package sv.ues.fmo.poo2.citas_medicas_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.CitaRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.CitaResponseDTO;
import sv.ues.fmo.poo2.citas_medicas_api.model.Cita;
import sv.ues.fmo.poo2.citas_medicas_api.model.Especialidad;
import sv.ues.fmo.poo2.citas_medicas_api.model.Medico;
import sv.ues.fmo.poo2.citas_medicas_api.model.Paciente;
import sv.ues.fmo.poo2.citas_medicas_api.repository.CitaRepository;
import sv.ues.fmo.poo2.citas_medicas_api.repository.EspecialidadRepository;
import sv.ues.fmo.poo2.citas_medicas_api.repository.MedicoRepository;
import sv.ues.fmo.poo2.citas_medicas_api.repository.PacienteRepository;
import sv.ues.fmo.poo2.citas_medicas_api.service.CitaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public List<CitaResponseDTO> obtenerTodas() {
        return citaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public CitaResponseDTO agendarCita(CitaRequestDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + dto.getPacienteId()));
        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + dto.getMedicoId()));
        Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + dto.getEspecialidadId()));

        Cita cita = Cita.builder()
                .paciente(paciente)
                .medico(medico)
                .especialidad(especialidad)
                .fechaCita(dto.getFechaCita())
                .horaCita(dto.getHoraCita())
                .estadoCita("PENDIENTE")
                .motivoCita(dto.getMotivoCita())
                .build();

        Cita citaGuardada = citaRepository.save(cita);
        return convertirADTO(citaGuardada);
    }

    private CitaResponseDTO convertirADTO(Cita cita) {
        return CitaResponseDTO.builder()
                .idCita(cita.getIdCita())
                .nombrePaciente(cita.getPaciente().getNombrePaciente() + " " + cita.getPaciente().getApellidoPaciente())
                .nombreMedico(cita.getMedico().getNombreMedico() + " " + cita.getMedico().getApellidoMedico())
                .nombreEspecialidad(cita.getEspecialidad().getNombreEspecialidad())
                .fechaCita(cita.getFechaCita())
                .horaCita(cita.getHoraCita())
                .estadoCita(cita.getEstadoCita())
                .motivoCita(cita.getMotivoCita())
                .build();
    }
}