package sv.ues.fmo.poo2.citas_medicas_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.MedicoRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.MedicoResponseDto;
import sv.ues.fmo.poo2.citas_medicas_api.service.MedicoService;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<MedicoResponseDto>> listarMedicos() {
        return ResponseEntity.ok(medicoService.listarMedicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntity<MedicoResponseDto>> obtenerMedicoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseEntity.ok(medicoService.obtenerMedicoPorId(id)));
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDto> guardarMedico(@RequestBody MedicoRequestDto requestDto) {
        MedicoResponseDto nuevoMedico = medicoService.guardarMedico(requestDto);
        return new ResponseEntity<>(nuevoMedico, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMedico(@PathVariable Long id) {
        medicoService.eliminarMedico(id);
        return ResponseEntity.ok("Médico eliminado correctamente.");
    }
}