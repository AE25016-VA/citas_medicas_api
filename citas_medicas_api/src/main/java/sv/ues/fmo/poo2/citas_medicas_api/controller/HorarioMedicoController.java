package sv.ues.fmo.poo2.citas_medicas_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.HorarioMedicoRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.HorarioMedicoResponseDTO;
import sv.ues.fmo.poo2.citas_medicas_api.service.HorarioMedicoService;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioMedicoController {

    private final HorarioMedicoService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioMedicoResponseDTO>> listarHorarios() {
        return ResponseEntity.ok(horarioService.listarHorarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioMedicoResponseDTO> obtenerHorarioPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(horarioService.obtenerHorarioPorId(id));
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<HorarioMedicoResponseDTO>> listarPorMedico(
            @PathVariable Long idMedico) {

        return ResponseEntity.ok(
                horarioService.listarHorariosPorMedico(idMedico));
    }

    @PostMapping
    public ResponseEntity<HorarioMedicoResponseDTO> guardarHorario(
            @Valid @RequestBody HorarioMedicoRequestDTO requestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(horarioService.guardarHorario(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioMedicoResponseDTO> actualizarHorario(
            @PathVariable Long id,
            @Valid @RequestBody HorarioMedicoRequestDTO requestDTO) {

        return ResponseEntity.ok(
                horarioService.actualizarHorario(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long id) {
        horarioService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
}