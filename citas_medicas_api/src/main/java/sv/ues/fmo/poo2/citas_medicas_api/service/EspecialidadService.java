package sv.ues.fmo.poo2.citas_medicas_api.service;

import sv.ues.fmo.poo2.citas_medicas_api.dto.request.EspecialidadRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.EspecialidadResponseDto;

import java.util.List;

public interface EspecialidadService {
    List<EspecialidadResponseDto> listarEspecialidades();
    EspecialidadResponseDto obtenerEspecialidadPorId(Long id);
    EspecialidadResponseDto guardarEspecialidad(EspecialidadRequestDto requestDto);
    EspecialidadResponseDto actualizarEspecialidad(Long id, EspecialidadRequestDto requestDto);
    void eliminarEspecialidad(Long id);
}

