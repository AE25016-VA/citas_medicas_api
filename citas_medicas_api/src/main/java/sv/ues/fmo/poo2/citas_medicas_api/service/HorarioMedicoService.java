package sv.ues.fmo.poo2.citas_medicas_api.service;

import sv.ues.fmo.poo2.citas_medicas_api.dto.request.HorarioMedicoRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.HorarioMedicoResponseDTO;

import java.util.List;

public interface HorarioMedicoService {

    List<HorarioMedicoResponseDTO> listarHorarios();

    HorarioMedicoResponseDTO obtenerHorarioPorId(Long id);

    List<HorarioMedicoResponseDTO> listarHorariosPorMedico(Long idMedico);

    HorarioMedicoResponseDTO guardarHorario(
            HorarioMedicoRequestDTO requestDTO);

    HorarioMedicoResponseDTO actualizarHorario(
            Long id, HorarioMedicoRequestDTO requestDTO);

    void eliminarHorario(Long id);
}