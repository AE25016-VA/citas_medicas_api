package sv.ues.fmo.poo2.citas_medicas_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.MedicoRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.MedicoResponseDto;
import sv.ues.fmo.poo2.citas_medicas_api.model.Medico;
import sv.ues.fmo.poo2.citas_medicas_api.repository.MedicoRepository;
import sv.ues.fmo.poo2.citas_medicas_api.service.MedicoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public List<MedicoResponseDto> listarMedicos() {
        return medicoRepository.findAll().stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    public MedicoResponseDto obtenerMedicoPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con el ID: " + id));
        return mapearADto(medico);
    }

    @Override
    public MedicoResponseDto guardarMedico(MedicoRequestDto requestDto) {
        Medico medico = new Medico();
        medico.setNombreMedico(requestDto.getNombreMedico());
        medico.setApellidoMedico(requestDto.getApellidoMedico());
        medico.setCorreoMedico(requestDto.getCorreoMedico());
        medico.setNombreUsuario(requestDto.getNombreUsuario());
        medico.setContrasena(requestDto.getContrasena());
        medico.setGeneroMedico(requestDto.getGeneroMedico());

        Medico medicoGuardado = medicoRepository.save(medico);
        return mapearADto(medicoGuardado);
    }

    @Override
    public void eliminarMedico(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, médico no encontrado con ID: " + id);
        }
        medicoRepository.deleteById(id);
    }

    private MedicoResponseDto mapearADto(Medico medico) {
        MedicoResponseDto dto = new MedicoResponseDto();
        dto.setIdMedico(medico.getIdMedico());
        dto.setNombreMedico(medico.getNombreMedico());
        dto.setApellidoMedico(medico.getApellidoMedico());
        dto.setCorreoMedico(medico.getCorreoMedico());
        dto.setNombreUsuario(medico.getNombreUsuario());
        return dto;
    }
    
    @Override
    public MedicoResponseDto actualizarMedico(Long id, MedicoRequestDto requestDto) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con el ID: " + id));

        medico.setNombreMedico(requestDto.getNombreMedico());
        medico.setApellidoMedico(requestDto.getApellidoMedico());
        medico.setCorreoMedico(requestDto.getCorreoMedico());
        medico.setNombreUsuario(requestDto.getNombreUsuario());
        medico.setContrasena(requestDto.getContrasena());
        medico.setGeneroMedico(requestDto.getGeneroMedico());

        Medico medicoActualizado = medicoRepository.save(medico);
        return mapearADto(medicoActualizado);
    }
}