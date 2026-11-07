package sv.ues.fmo.poo2.citas_medicas_api.service;

import sv.ues.fmo.poo2.citas_medicas_api.dto.request.HorarioMedicoRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.HorarioMedicoResponseDto;

import java.util.List;

public interface HorarioMedicoService {
    List<HorarioMedicoResponseDto> listarHorarios();
    List<HorarioMedicoResponseDto> obtenerHorariosPorMedico(Long medicoId);
    HorarioMedicoResponseDto obtenerHorarioPorId(Long id);
    HorarioMedicoResponseDto guardarHorario(HorarioMedicoRequestDto requestDto);
    HorarioMedicoResponseDto actualizarHorario(Long id, HorarioMedicoRequestDto requestDto);
    void eliminarHorario(Long id);
}