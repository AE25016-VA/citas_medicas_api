package sv.ues.fmo.poo2.citas_medicas_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.HorarioMedicoRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.HorarioMedicoResponseDto;
import sv.ues.fmo.poo2.citas_medicas_api.service.HorarioMedicoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/horarios")
public class HorarioMedicoController {

    @Autowired
    private HorarioMedicoService horarioMedicoService;

    @GetMapping
    public ResponseEntity<List<HorarioMedicoResponseDto>> listarHorarios() {
        return ResponseEntity.ok(horarioMedicoService.listarHorarios());
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<HorarioMedicoResponseDto>> obtenerHorariosPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(horarioMedicoService.obtenerHorariosPorMedico(medicoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioMedicoResponseDto> obtenerHorarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(horarioMedicoService.obtenerHorarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<HorarioMedicoResponseDto> guardarHorario(@Valid @RequestBody HorarioMedicoRequestDto requestDto) {
        return ResponseEntity.ok(horarioMedicoService.guardarHorario(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioMedicoResponseDto> actualizarHorario(
            @PathVariable Long id,
            @Valid @RequestBody HorarioMedicoRequestDto requestDto) {
        return ResponseEntity.ok(horarioMedicoService.actualizarHorario(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long id) {
        horarioMedicoService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
}