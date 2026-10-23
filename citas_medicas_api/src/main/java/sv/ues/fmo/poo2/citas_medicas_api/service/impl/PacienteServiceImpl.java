package sv.ues.fmo.poo2.citas_medicas_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.PacienteRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.PacienteResponseDTO;
import sv.ues.fmo.poo2.citas_medicas_api.exception.BadRequestException;
import sv.ues.fmo.poo2.citas_medicas_api.exception.ResourceNotFoundException;
import sv.ues.fmo.poo2.citas_medicas_api.model.Paciente;
import sv.ues.fmo.poo2.citas_medicas_api.repository.PacienteRepository;
import sv.ues.fmo.poo2.citas_medicas_api.service.PacienteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> obtenerTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));
        return mapToDTO(paciente);
    }

    @Override
    @Transactional
    public PacienteResponseDTO crearPaciente(PacienteRequestDTO requestDTO) {
        if (pacienteRepository.existsByCorreoPaciente(requestDTO.getCorreoPaciente())) {
            throw new BadRequestException("El correo ya está registrado: " + requestDTO.getCorreoPaciente());
        }

        Paciente paciente = Paciente.builder()
                .nombrePaciente(requestDTO.getNombrePaciente())
                .apellidoPaciente(requestDTO.getApellidoPaciente())
                .correoPaciente(requestDTO.getCorreoPaciente())
                .contrasenaPaciente(requestDTO.getContrasenaPaciente())
                .telefonoPaciente(requestDTO.getTelefonoPaciente())
                .build();

        return mapToDTO(pacienteRepository.save(paciente));
    }

    @Override
    @Transactional
    public PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO requestDTO) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));

        paciente.setNombrePaciente(requestDTO.getNombrePaciente());
        paciente.setApellidoPaciente(requestDTO.getApellidoPaciente());
        paciente.setCorreoPaciente(requestDTO.getCorreoPaciente());
        paciente.setContrasenaPaciente(requestDTO.getContrasenaPaciente());
        paciente.setTelefonoPaciente(requestDTO.getTelefonoPaciente());

        return mapToDTO(pacienteRepository.save(paciente));
    }

    @Override
    @Transactional
    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    private PacienteResponseDTO mapToDTO(Paciente paciente) {
        return PacienteResponseDTO.builder()
                .idPaciente(paciente.getIdPaciente())
                .nombrePaciente(paciente.getNombrePaciente())
                .apellidoPaciente(paciente.getApellidoPaciente())
                .correoPaciente(paciente.getCorreoPaciente())
                .telefonoPaciente(paciente.getTelefonoPaciente())
                .build();
    }
}
