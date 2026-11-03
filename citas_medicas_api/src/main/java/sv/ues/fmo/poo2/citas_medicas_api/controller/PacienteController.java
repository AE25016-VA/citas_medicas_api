package sv.ues.fmo.poo2.citas_medicas_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.PacienteRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.PacienteResponseDTO;
import sv.ues.fmo.poo2.citas_medicas_api.service.PacienteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crearPaciente(@Valid @RequestBody PacienteRequestDTO requestDTO) {
        return new ResponseEntity<>(pacienteService.crearPaciente(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO requestDTO) {
        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}