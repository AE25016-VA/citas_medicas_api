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
class HorarioMedicoUnitTest {
 HorarioMedicoRepository repo = mock(HorarioMedicoRepository.class);
 MedicoRepository medicos = mock(MedicoRepository.class);
 HorarioMedicoServiceImpl servicio = new HorarioMedicoServiceImpl(repo, medicos);
 HorarioMedicoRequestDto request() { HorarioMedicoRequestDto r = new HorarioMedicoRequestDto(); r.setMedicoId(2L); r.setDiasTurno("LUNES"); r.setHoraInicio(LocalTime.of(8,0)); r.setHoraFin(LocalTime.of(12,0)); r.setDuracionConsultaMinutos(30); return r; }
 @Test void listaVacia() { when(repo.findAll()).thenReturn(List.of()); assertTrue(servicio.listarHorarios().isEmpty()); }
 @Test void consultaInexistente() { when(repo.findById(99L)).thenReturn(Optional.empty()); assertThrows(RuntimeException.class, () -> servicio.obtenerHorarioPorId(99L)); }
 @Test void actualizacionInexistenteNoGuarda() { assertThrows(RuntimeException.class, () -> servicio.actualizarHorario(99L,request())); verify(repo,never()).save(any()); }
 @Test void eliminaExistente() { when(repo.existsById(1L)).thenReturn(true); servicio.eliminarHorario(1L); verify(repo).deleteById(1L); }
 @Test void noEliminaInexistente() { assertThrows(RuntimeException.class, () -> servicio.eliminarHorario(99L)); verify(repo,never()).deleteById(anyLong()); }
 @Test void creaYDevuelveIdGenerado() { Medico m = new Medico(); m.setIdMedico(2L); m.setNombreMedico("Ana"); m.setApellidoMedico("Prueba"); when(medicos.findById(2L)).thenReturn(Optional.of(m));
 when(repo.save(any())).thenAnswer(i -> { HorarioMedico e=i.getArgument(0); e.setIdHorario(7L); return e; });
 var resultado=servicio.guardarHorario(request()); assertEquals(7L,resultado.getIdHorario());
 var captor=org.mockito.ArgumentCaptor.forClass(HorarioMedico.class); verify(repo).save(captor.capture());
assertEquals(2L,resultado.getMedicoId()); assertEquals(LocalTime.of(12,0),captor.getValue().getHoraFin()); assertEquals(30,resultado.getDuracionConsultaMinutos());
}
@Test void medicoInexistenteNoGuarda() { assertThrows(RuntimeException.class, () -> servicio.guardarHorario(request())); verify(repo,never()).save(any()); }
@Test void actualizaConservandoId() {
 var e=new HorarioMedico(); e.setIdHorario(5L); when(repo.findById(5L)).thenReturn(Optional.of(e)); var m=new Medico(); m.setIdMedico(2L); when(medicos.findById(2L)).thenReturn(Optional.of(m));
 when(repo.save(any())).thenAnswer(i->i.getArgument(0));
 var resultado=servicio.actualizarHorario(5L,request());
 assertEquals(5L,resultado.getIdHorario()); verify(repo).save(e);
 }
}
