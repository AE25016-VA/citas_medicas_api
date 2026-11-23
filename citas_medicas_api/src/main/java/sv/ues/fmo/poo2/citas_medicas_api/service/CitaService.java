package sv.ues.fmo.poo2.citas_medicas_api.service;

import sv.ues.fmo.poo2.citas_medicas_api.dto.request.CitaRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.CitaResponseDTO;

import java.util.List;

public interface CitaService {

    List<CitaResponseDTO> listarCitas();

    CitaResponseDTO obtenerCitaPorId(Long id);

    List<CitaResponseDTO> obtenerCitasPorPaciente(Long pacienteId);

    List<CitaResponseDTO> obtenerCitasPorMedico(Long medicoId);

    CitaResponseDTO crearCita(CitaRequestDTO requestDTO);

    CitaResponseDTO actualizarCita(Long id, CitaRequestDTO requestDTO);

    void cancelarCita(Long id);
}
