package sv.ues.fmo.poo2.citas_medicas_api.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.MedicoRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.EspecialidadResponseDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.MedicoResponseDto;
import sv.ues.fmo.poo2.citas_medicas_api.model.Especialidad;
import sv.ues.fmo.poo2.citas_medicas_api.model.Medico;
import sv.ues.fmo.poo2.citas_medicas_api.repository.EspecialidadRepository;
import sv.ues.fmo.poo2.citas_medicas_api.repository.MedicoRepository;
import sv.ues.fmo.poo2.citas_medicas_api.service.MedicoService;

@Service
@RequiredArgsConstructor // Usamos inyección por constructor 
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MedicoResponseDto> listarMedicos() {
        return medicoRepository.findAll().stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MedicoResponseDto obtenerMedicoPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con el ID: " + id));
        return mapearADto(medico);
    }

    @Override
    @Transactional
    public MedicoResponseDto guardarMedico(MedicoRequestDto requestDto) {
        Medico medico = new Medico();
        medico.setNombreMedico(requestDto.getNombreMedico());
        medico.setApellidoMedico(requestDto.getApellidoMedico());
        medico.setCorreoMedico(requestDto.getCorreoMedico());
        medico.setNombreUsuario(requestDto.getNombreUsuario());
        medico.setContrasena(requestDto.getContrasena());
        medico.setGeneroMedico(requestDto.getGeneroMedico());

        if (requestDto.getEspecialidadesIds() != null && !requestDto.getEspecialidadesIds().isEmpty()) {
            Set<Especialidad> especialidades = new HashSet<>(
                especialidadRepository.findAllById(requestDto.getEspecialidadesIds())
            );
            medico.setEspecialidades(especialidades);
        }

        Medico medicoGuardado = medicoRepository.save(medico);
        return mapearADto(medicoGuardado);
    }

   @Override
@Transactional
public MedicoResponseDto actualizarMedico(Long id, MedicoRequestDto requestDto) {
    Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Médico no encontrado con el ID: " + id));

    // Validar correo único (ignorando si le pertenece al mismo médico)
    medicoRepository.findByCorreoMedico(requestDto.getCorreoMedico()).ifPresent(m -> {
        if (!m.getIdMedico().equals(id)) {
            throw new RuntimeException("El correo ya pertenece a otro médico.");
        }
    });

    // Validar usuario único (ignorando si le pertenece al mismo médico)
    medicoRepository.findByNombreUsuario(requestDto.getNombreUsuario()).ifPresent(m -> {
        if (!m.getIdMedico().equals(id)) {
            throw new RuntimeException("El nombre de usuario ya pertenece a otro médico.");
        }
    });

    medico.setNombreMedico(requestDto.getNombreMedico());
    medico.setApellidoMedico(requestDto.getApellidoMedico());
    medico.setCorreoMedico(requestDto.getCorreoMedico());
    medico.setNombreUsuario(requestDto.getNombreUsuario());
    medico.setContrasena(requestDto.getContrasena());
    medico.setGeneroMedico(requestDto.getGeneroMedico());

    if (requestDto.getEspecialidadesIds() != null) {
        List<Especialidad> especialidadesEncontradas = 
                especialidadRepository.findAllById(requestDto.getEspecialidadesIds());

        if (medico.getEspecialidades() == null) {
            medico.setEspecialidades(new HashSet<>());
        }

        medico.getEspecialidades().clear();
        medico.getEspecialidades().addAll(especialidadesEncontradas);
    }

    Medico medicoActualizado = medicoRepository.save(medico);
    return mapearADto(medicoActualizado);
}
    @Override
    @Transactional
    public void eliminarMedico(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, médico no encontrado con ID: " + id);
        }
        medicoRepository.deleteById(id);
    }

    private MedicoResponseDto mapearADto(Medico medico) {
        MedicoResponseDto dto = new MedicoResponseDto();
        dto.setIdMedico(medico.getIdMedico());
        dto.setNombreMedico(medico.getNombreMedico());
        dto.setApellidoMedico(medico.getApellidoMedico());
        dto.setCorreoMedico(medico.getCorreoMedico());
        dto.setNombreUsuario(medico.getNombreUsuario());
        dto.setGeneroMedico(medico.getGeneroMedico());

        if (medico.getEspecialidades() != null && !medico.getEspecialidades().isEmpty()) {
            List<EspecialidadResponseDto> especialidadesDto = medico.getEspecialidades().stream()
                    .map(esp -> {
                        EspecialidadResponseDto espDto = new EspecialidadResponseDto();
                        espDto.setIdEspecialidad(esp.getIdEspecialidad());
                        espDto.setNombreEspecialidad(esp.getNombreEspecialidad());
                        espDto.setDescripcionEspecialidad(esp.getDescripcionEspecialidad());
                        return espDto;
                    })
                    .collect(Collectors.toList());

            dto.setEspecialidades(especialidadesDto);
        } else {
            dto.setEspecialidades(new ArrayList<>());
        }

        return dto;
    }
}