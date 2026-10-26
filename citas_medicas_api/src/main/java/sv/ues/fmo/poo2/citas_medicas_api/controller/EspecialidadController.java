package sv.ues.fmo.poo2.citas_medicas_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sv.ues.fmo.poo2.citas_medicas_api.model.Especialidad;
import sv.ues.fmo.poo2.citas_medicas_api.repository.EspecialidadRepository;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    // Obtener todas las especialidades
    @GetMapping
    public List<Especialidad> obtenerTodas() {
        return especialidadRepository.findAll();
    }

    // Crear una nueva especialidad
    @PostMapping
    public Especialidad guardar(@RequestBody Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }
}
