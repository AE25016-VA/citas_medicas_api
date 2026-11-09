package sv.ues.fmo.poo2.citas_medicas_api.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.EspecialidadRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.EspecialidadResponseDto;
import sv.ues.fmo.poo2.citas_medicas_api.model.Especialidad;
import sv.ues.fmo.poo2.citas_medicas_api.repository.EspecialidadRepository;
import sv.ues.fmo.poo2.citas_medicas_api.service.EspecialidadService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor 
public class EspecialidadServiceImpl implements EspecialidadService {

    
    private final EspecialidadRepository especialidadRepository;

    @Override
    public List<EspecialidadResponseDto> listarEspecialidades() {
        return especialidadRepository.findAll().stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    public EspecialidadResponseDto obtenerEspecialidadPorId(Long id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
        return mapearADto(especialidad);
    }

    @Override
    public EspecialidadResponseDto guardarEspecialidad(EspecialidadRequestDto requestDto) {
        // Validar si el nombre de la especialidad ya existe
        especialidadRepository.findByNombreEspecialidad(requestDto.getNombreEspecialidad())
                .ifPresent(e -> {
                    throw new RuntimeException("Ya existe una especialidad registrada con el nombre: " + requestDto.getNombreEspecialidad());
                });

        Especialidad especialidad = new Especialidad();
        especialidad.setNombreEspecialidad(requestDto.getNombreEspecialidad());
        especialidad.setDescripcionEspecialidad(requestDto.getDescripcionEspecialidad());

        Especialidad guardada = especialidadRepository.save(especialidad);
        return mapearADto(guardada);
    }

    @Override
    public void eliminarEspecialidad(Long id) {
        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, especialidad no encontrada con ID: " + id);
        }
        especialidadRepository.deleteById(id);
    }

    private EspecialidadResponseDto mapearADto(Especialidad especialidad) {
        EspecialidadResponseDto dto = new EspecialidadResponseDto();
        dto.setIdEspecialidad(especialidad.getIdEspecialidad());
        dto.setNombreEspecialidad(especialidad.getNombreEspecialidad());
        dto.setDescripcionEspecialidad(especialidad.getDescripcionEspecialidad());
        return dto;
    }

    @Override
public EspecialidadResponseDto actualizarEspecialidad(Long id, EspecialidadRequestDto requestDto) {
    Especialidad especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));

    // Validar si el nuevo nombre ya pertenece a OTRA especialidad registrada
    especialidadRepository.findByNombreEspecialidad(requestDto.getNombreEspecialidad())
            .ifPresent(e -> {
                if (!e.getIdEspecialidad().equals(id)) {
                    throw new RuntimeException("Ya existe otra especialidad con el nombre: " + requestDto.getNombreEspecialidad());
                }
            });

    especialidad.setNombreEspecialidad(requestDto.getNombreEspecialidad());
    especialidad.setDescripcionEspecialidad(requestDto.getDescripcionEspecialidad());

    Especialidad actualizada = especialidadRepository.save(especialidad);
    return mapearADto(actualizada);
}
}