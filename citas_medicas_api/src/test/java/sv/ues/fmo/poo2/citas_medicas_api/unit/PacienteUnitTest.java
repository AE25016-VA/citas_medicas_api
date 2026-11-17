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
class PacienteUnitTest {
 PacienteRepository repo = mock(PacienteRepository.class);
 
 PacienteServiceImpl servicio = new PacienteServiceImpl(repo);
 PacienteRequestDTO request() { PacienteRequestDTO r = new PacienteRequestDTO(); r.setNombrePaciente("Luis"); r.setApellidoPaciente("Prueba"); r.setCorreoPaciente("luis@example.com"); r.setContrasenaPaciente("Prueba123"); r.setTelefonoPaciente("77777777"); return r; }
 @Test void listaVacia() { when(repo.findAll()).thenReturn(List.of()); assertTrue(servicio.obtenerTodos().isEmpty()); }
 @Test void consultaInexistente() { when(repo.findById(99L)).thenReturn(Optional.empty()); assertThrows(RuntimeException.class, () -> servicio.obtenerPorId(99L)); }
 @Test void actualizacionInexistenteNoGuarda() { assertThrows(RuntimeException.class, () -> servicio.actualizarPaciente(99L,request())); verify(repo,never()).save(any()); }
 @Test void eliminaExistente() { when(repo.existsById(1L)).thenReturn(true); servicio.eliminarPaciente(1L); verify(repo).deleteById(1L); }
 @Test void noEliminaInexistente() { assertThrows(RuntimeException.class, () -> servicio.eliminarPaciente(99L)); verify(repo,never()).deleteById(anyLong()); }
 @Test void creaYDevuelveIdGenerado() { 
 when(repo.save(any())).thenAnswer(i -> { Paciente e=i.getArgument(0); e.setIdPaciente(7L); return e; });
 var resultado=servicio.crearPaciente(request()); assertEquals(7L,resultado.getIdPaciente());
 var captor=org.mockito.ArgumentCaptor.forClass(Paciente.class); verify(repo).save(captor.capture());
assertEquals("luis@example.com",captor.getValue().getCorreoPaciente()); assertEquals("Luis",resultado.getNombrePaciente());
}
@Test void correoDuplicadoNoGuarda() { when(repo.existsByCorreoPaciente("luis@example.com")).thenReturn(true); assertThrows(sv.ues.fmo.poo2.citas_medicas_api.exception.BadRequestException.class, () -> servicio.crearPaciente(request())); verify(repo,never()).save(any()); }
@Test void actualizaConservandoId() {
 var e=new Paciente(); e.setIdPaciente(5L); when(repo.findById(5L)).thenReturn(Optional.of(e)); 
 when(repo.save(any())).thenAnswer(i->i.getArgument(0));
 var resultado=servicio.actualizarPaciente(5L,request());
 assertEquals(5L,resultado.getIdPaciente()); verify(repo).save(e);
 }
}
