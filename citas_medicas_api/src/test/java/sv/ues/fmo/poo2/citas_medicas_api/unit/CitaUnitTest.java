package sv.ues.fmo.poo2.citas_medicas_api.unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import java.time.*;
import sv.ues.fmo.poo2.citas_medicas_api.model.*;
import sv.ues.fmo.poo2.citas_medicas_api.repository.*;
import sv.ues.fmo.poo2.citas_medicas_api.service.impl.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.*;
@ExtendWith(MockitoExtension.class)
class CitaUnitTest {
 CitaRepository citas=mock(CitaRepository.class);
 PacienteRepository pacientes=mock(PacienteRepository.class);
 MedicoRepository medicos=mock(MedicoRepository.class);
 EspecialidadRepository especialidades=mock(EspecialidadRepository.class);
 HorarioMedicoRepository horarios=mock(HorarioMedicoRepository.class);
 CitaServiceImpl servicio=new CitaServiceImpl(citas,pacientes,medicos,especialidades,horarios);
 CitaRequestDTO request() { var r=new CitaRequestDTO(); r.setPacienteId(1L); r.setMedicoId(2L); r.setEspecialidadId(3L); r.setFechaCita(LocalDate.of(2027,1,4)); r.setHoraCita(LocalTime.of(9,0)); r.setMotivoConsulta("Control"); return r; }
 Medico prepararPersonas() { var p=new Paciente(); p.setIdPaciente(1L); var e=new Especialidad(); e.setIdEspecialidad(3L); e.setNombreEspecialidad("General"); var m=new Medico(); m.setIdMedico(2L); m.setEspecialidades(new HashSet<>(Set.of(e))); when(pacientes.findById(1L)).thenReturn(Optional.of(p)); when(medicos.findById(2L)).thenReturn(Optional.of(m)); when(especialidades.findById(3L)).thenReturn(Optional.of(e)); return m; }
 void prepararTurno() { var m=prepararPersonas(); var h=HorarioMedico.builder().medico(m).diasTurno("LUNES").horaInicio(LocalTime.of(8,0)).horaFin(LocalTime.of(12,0)).duracionConsultaMinutos(30).build(); when(horarios.findByMedicoIdMedico(2L)).thenReturn(List.of(h)); }
 @Test void listaVacia() { assertTrue(servicio.listarCitas().isEmpty()); }
 @Test void citaInexistente() { assertThrows(RuntimeException.class,()->servicio.obtenerCitaPorId(99L)); }
 @Test void actualizarInexistenteNoGuarda() { assertThrows(RuntimeException.class,()->servicio.actualizarCita(99L,request())); verify(citas,never()).save(any()); }
 @Test void cancelarInexistenteNoGuarda() { assertThrows(RuntimeException.class,()->servicio.cancelarCita(99L)); verify(citas,never()).save(any()); }
 @Test void cancelaSinEliminar() { var c=new Cita(); c.setEstadoCita("CONFIRMADA"); when(citas.findById(1L)).thenReturn(Optional.of(c)); servicio.cancelarCita(1L); assertEquals("CANCELADA",c.getEstadoCita()); verify(citas).save(c); verify(citas,never()).deleteById(anyLong()); }
 @Test void pacienteInexistenteNoGuarda() { assertThrows(RuntimeException.class,()->servicio.crearCita(request())); verify(citas,never()).save(any()); }
 @Test void especialidadAjenaNoGuarda() { var m=prepararPersonas(); m.getEspecialidades().clear(); assertThrows(IllegalArgumentException.class,()->servicio.crearCita(request())); verify(citas,never()).save(any()); }
 @Test void sinTurnoNoGuarda() { prepararPersonas(); assertThrows(IllegalArgumentException.class,()->servicio.crearCita(request())); verify(citas,never()).save(any()); }
 @Test void excedeFinDelTurno() { prepararTurno(); var r=request(); r.setHoraCita(LocalTime.of(11,45)); assertThrows(IllegalArgumentException.class,()->servicio.crearCita(r)); verify(citas,never()).save(any()); }
 @Test void solapamientoNoGuarda() { prepararTurno(); var r=request(); when(citas.existeSolapamientoCita(2L,r.getFechaCita(),r.getHoraCita(),LocalTime.of(9,30),null)).thenReturn(true); assertThrows(IllegalArgumentException.class,()->servicio.crearCita(r)); verify(citas,never()).save(any()); }
 @Test void creaConfirmadaYCalculaHoraFin() { prepararTurno(); when(citas.save(any())).thenAnswer(i->{ Cita c=i.getArgument(0); c.setIdCita(7L); return c; }); var r=servicio.crearCita(request()); assertEquals(7L,r.getIdCita()); assertEquals("CONFIRMADA",r.getEstadoCita()); assertEquals(LocalTime.of(9,30),r.getHoraFinCita()); verify(citas).existeSolapamientoCita(2L,LocalDate.of(2027,1,4),LocalTime.of(9,0),LocalTime.of(9,30),null); }
}
