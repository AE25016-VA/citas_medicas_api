package sv.ues.fmo.poo2.citas_medicas_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.CitaRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.CitaResponseDTO;
import sv.ues.fmo.poo2.citas_medicas_api.service.CitaService;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> agendarCita(@RequestBody CitaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.agendarCita(dto));
    }
}
