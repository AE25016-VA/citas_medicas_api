package sv.ues.fmo.poo2.citas_medicas_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.CitaRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.CitaResponseDTO;
import sv.ues.fmo.poo2.citas_medicas_api.service.CitaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listarCitas() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerCitaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerCitaPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaResponseDTO>> obtenerCitasPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.obtenerCitasPorPaciente(pacienteId));
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<CitaResponseDTO>> obtenerCitasPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(citaService.obtenerCitasPorMedico(medicoId));
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> crearCita(@Valid @RequestBody CitaRequestDTO requestDTO) {
        return new ResponseEntity<>(citaService.crearCita(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> actualizarCita(
            @PathVariable Long id,
            @Valid @RequestBody CitaRequestDTO requestDTO) {
        return ResponseEntity.ok(citaService.actualizarCita(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarCita(@PathVariable Long id) {
        citaService.cancelarCita(id);
        return ResponseEntity.noContent().build();
    }
}