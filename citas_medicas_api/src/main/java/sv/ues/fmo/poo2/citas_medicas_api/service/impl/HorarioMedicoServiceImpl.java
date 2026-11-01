package sv.ues.fmo.poo2.citas_medicas_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.HorarioMedicoRequestDTO;
import sv.ues.fmo.poo2.citas_medicas_api.dto.response.HorarioMedicoResponseDTO;
import sv.ues.fmo.poo2.citas_medicas_api.exception.BadRequestException;
import sv.ues.fmo.poo2.citas_medicas_api.exception.ResourceNotFoundException;
import sv.ues.fmo.poo2.citas_medicas_api.model.HorarioMedico;
import sv.ues.fmo.poo2.citas_medicas_api.model.Medico;
import sv.ues.fmo.poo2.citas_medicas_api.repository.HorarioMedicoRepository;
import sv.ues.fmo.poo2.citas_medicas_api.repository.MedicoRepository;
import sv.ues.fmo.poo2.citas_medicas_api.service.HorarioMedicoService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HorarioMedicoServiceImpl implements HorarioMedicoService {

    private final HorarioMedicoRepository horarioRepository;
    private final MedicoRepository medicoRepository;

    @Override
    public List<HorarioMedicoResponseDTO> listarHorarios() {
        return horarioRepository.findAll()
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    @Override
    public HorarioMedicoResponseDTO obtenerHorarioPorId(Long id) {
        return convertirRespuesta(buscarHorario(id));
    }

    @Override
    public List<HorarioMedicoResponseDTO> listarHorariosPorMedico(
            Long idMedico) {

        buscarMedico(idMedico);

        return horarioRepository.findByMedico_IdMedico(idMedico)
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    @Override
    @Transactional
    public HorarioMedicoResponseDTO guardarHorario(
            HorarioMedicoRequestDTO requestDTO) {

        validarHoras(requestDTO);
        Medico medico = buscarMedico(requestDTO.getIdMedico());

        HorarioMedico horario = new HorarioMedico();
        asignarDatos(horario, requestDTO, medico);

        return convertirRespuesta(horarioRepository.save(horario));
    }

    @Override
    @Transactional
    public HorarioMedicoResponseDTO actualizarHorario(
            Long id, HorarioMedicoRequestDTO requestDTO) {

        HorarioMedico horario = buscarHorario(id);
        validarHoras(requestDTO);
        Medico medico = buscarMedico(requestDTO.getIdMedico());

        asignarDatos(horario, requestDTO, medico);

        return convertirRespuesta(horarioRepository.save(horario));
    }

    @Override
    @Transactional
    public void eliminarHorario(Long id) {
        HorarioMedico horario = buscarHorario(id);
        horarioRepository.delete(horario);
    }

    private HorarioMedico buscarHorario(Long id) {
        return horarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Horario no encontrado con ID: " + id));
    }

    private Medico buscarMedico(Long idMedico) {
        return medicoRepository.findById(idMedico)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Médico no encontrado con ID: " + idMedico));
    }

    private void validarHoras(HorarioMedicoRequestDTO requestDTO) {
        if (requestDTO.getHoraInicio() == null
                || requestDTO.getHoraFin() == null) {
            throw new BadRequestException(
                    "La hora de inicio y la hora de fin son obligatorias.");
        }

        if (!requestDTO.getHoraFin().isAfter(requestDTO.getHoraInicio())) {
            throw new BadRequestException(
                    "La hora de fin debe ser posterior a la hora de inicio.");
        }
    }

    private void asignarDatos(
            HorarioMedico horario,
            HorarioMedicoRequestDTO requestDTO,
            Medico medico) {

        horario.setMedico(medico);
        horario.setDiasTurno(requestDTO.getDiasTurno());
        horario.setHoraInicio(requestDTO.getHoraInicio());
        horario.setHoraFin(requestDTO.getHoraFin());
    }

    private HorarioMedicoResponseDTO convertirRespuesta(
            HorarioMedico horario) {

        HorarioMedicoResponseDTO respuesta = new HorarioMedicoResponseDTO();
        respuesta.setIdHorario(horario.getIdHorario());
        respuesta.setIdMedico(horario.getMedico().getIdMedico());
        respuesta.setDiasTurno(horario.getDiasTurno());
        respuesta.setHoraInicio(horario.getHoraInicio());
        respuesta.setHoraFin(horario.getHoraFin());

        return respuesta;
    }
}