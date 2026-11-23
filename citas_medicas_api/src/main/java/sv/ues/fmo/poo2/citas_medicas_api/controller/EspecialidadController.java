package sv.ues.fmo.poo2.citas_medicas_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.EspecialidadRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.EspecialidadResponseDto;
import sv.ues.fmo.poo2.citas_medicas_api.service.EspecialidadService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<List<EspecialidadResponseDto>> listarEspecialidades() {
        return ResponseEntity.ok(especialidadService.listarEspecialidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDto> obtenerEspecialidadPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadService.obtenerEspecialidadPorId(id));
    }

    @PostMapping
    public ResponseEntity<EspecialidadResponseDto> guardarEspecialidad(@Valid @RequestBody EspecialidadRequestDto requestDto) {
        EspecialidadResponseDto nuevaEspecialidad = especialidadService.guardarEspecialidad(requestDto);
        return new ResponseEntity<>(nuevaEspecialidad, HttpStatus.CREATED);
    }
   @PutMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDto> actualizarEspecialidad(
        @PathVariable Long id,
        @Valid @RequestBody EspecialidadRequestDto requestDto) {
      EspecialidadResponseDto actualizada = especialidadService.actualizarEspecialidad(id, requestDto);
    return ResponseEntity.ok(actualizada);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspecialidad(@PathVariable Long id) {
        especialidadService.eliminarEspecialidad(id);
        return ResponseEntity.noContent().build();
    }
}