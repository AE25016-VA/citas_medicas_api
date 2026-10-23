package sv.ues.fmo.poo2.citas_medicas_api.service;

import sv.ues.fmo.poo2.citas_medicas_api.dto.request.CitaRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.CitaResponseDTO;

import java.util.List;

public interface CitaService {
    List<CitaResponseDTO> obtenerTodas();
    CitaResponseDTO agendarCita(CitaRequestDTO dto);
}