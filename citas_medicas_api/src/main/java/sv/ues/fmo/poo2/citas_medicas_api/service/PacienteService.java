package sv.ues.fmo.poo2.citas_medicas_api.service;

import sv.ues.fmo.poo2.citas_medicas_api.dto.request.PacienteRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.PacienteResponseDTO;
import java.util.List;

public interface PacienteService {
    List<PacienteResponseDTO> obtenerTodos();
    PacienteResponseDTO obtenerPorId(Long id);
    PacienteResponseDTO crearPaciente(PacienteRequestDTO requestDTO);
    PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO requestDTO);
    void eliminarPaciente(Long id);
}