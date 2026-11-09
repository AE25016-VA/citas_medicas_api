package sv.ues.fmo.poo2.citas_medicas_api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import sv.ues.fmo.poo2.citas_medicas_api.dto.request.HorarioMedicoRequestDto;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.HorarioMedicoResponseDto;
import sv.ues.fmo.poo2.citas_medicas_api.model.HorarioMedico;
import sv.ues.fmo.poo2.citas_medicas_api.model.Medico;
import sv.ues.fmo.poo2.citas_medicas_api.repository.HorarioMedicoRepository;
import sv.ues.fmo.poo2.citas_medicas_api.repository.MedicoRepository;
import sv.ues.fmo.poo2.citas_medicas_api.service.HorarioMedicoService;

@Service
@RequiredArgsConstructor
public class HorarioMedicoServiceImpl implements HorarioMedicoService {

    private final HorarioMedicoRepository horarioMedicoRepository;
    private final MedicoRepository medicoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioMedicoResponseDto> listarHorarios() {
        return horarioMedicoRepository.findAll().stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HorarioMedicoResponseDto> obtenerHorariosPorMedico(Long medicoId) {
        return horarioMedicoRepository.findByMedicoIdMedico(medicoId).stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioMedicoResponseDto obtenerHorarioPorId(Long id) {
        HorarioMedico horario = horarioMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));
        return mapearADto(horario);
    }

    @Override
    @Transactional
    public HorarioMedicoResponseDto guardarHorario(HorarioMedicoRequestDto requestDto) {
        Medico medico = medicoRepository.findById(requestDto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + requestDto.getMedicoId()));

        HorarioMedico horario = HorarioMedico.builder()
                .medico(medico)
                .diasTurno(requestDto.getDiasTurno())
                .horaInicio(requestDto.getHoraInicio())
                .horaFin(requestDto.getHoraFin())
                .duracionConsultaMinutos(requestDto.getDuracionConsultaMinutos())
                .build();

        return mapearADto(horarioMedicoRepository.save(horario));
    }

    @Override
    @Transactional
    public HorarioMedicoResponseDto actualizarHorario(Long id, HorarioMedicoRequestDto requestDto) {
        HorarioMedico horario = horarioMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));

        Medico medico = medicoRepository.findById(requestDto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + requestDto.getMedicoId()));

        horario.setMedico(medico);
        horario.setDiasTurno(requestDto.getDiasTurno());
        horario.setHoraInicio(requestDto.getHoraInicio());
        horario.setHoraFin(requestDto.getHoraFin());
        horario.setDuracionConsultaMinutos(requestDto.getDuracionConsultaMinutos());

        return mapearADto(horarioMedicoRepository.save(horario));
    }

    @Override
    @Transactional
    public void eliminarHorario(Long id) {
        if (!horarioMedicoRepository.existsById(id)) {
            throw new RuntimeException("Horario no encontrado con ID: " + id);
        }
        horarioMedicoRepository.deleteById(id);
    }

    private HorarioMedicoResponseDto mapearADto(HorarioMedico horario) {
        HorarioMedicoResponseDto dto = new HorarioMedicoResponseDto();
        dto.setIdHorario(horario.getIdHorario());
        dto.setMedicoId(horario.getMedico().getIdMedico());
        dto.setNombreCompletoMedico(horario.getMedico().getNombreMedico() + " " + horario.getMedico().getApellidoMedico());
        dto.setDiasTurno(horario.getDiasTurno());
        dto.setHoraInicio(horario.getHoraInicio());
        dto.setHoraFin(horario.getHoraFin());
        dto.setDuracionConsultaMinutos(horario.getDuracionConsultaMinutos());
        return dto;
    }
}