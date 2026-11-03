package sv.ues.fmo.poo2.citas_medicas_api.service;

import sv.ues.fmo.poo2.citas_medicas_api.dto.request.MedicoRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.MedicoResponseDto;

import java.util.List;

public interface MedicoService {
    List<MedicoResponseDto> listarMedicos();
    MedicoResponseDto obtenerMedicoPorId(Long id);
    MedicoResponseDto guardarMedico(MedicoRequestDto requestDto);
    MedicoResponseDto actualizarMedico(Long id, MedicoRequestDto requestDto);
    void eliminarMedico(Long id);
}