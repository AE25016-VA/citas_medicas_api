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
class EspecialidadUnitTest {
 EspecialidadRepository repo = mock(EspecialidadRepository.class);
 
 EspecialidadServiceImpl servicio = new EspecialidadServiceImpl(repo);
 EspecialidadRequestDto request() { EspecialidadRequestDto r = new EspecialidadRequestDto(); r.setNombreEspecialidad("General"); r.setDescripcionEspecialidad("Consulta general"); return r; }
 @Test void listaVacia() { when(repo.findAll()).thenReturn(List.of()); assertTrue(servicio.listarEspecialidades().isEmpty()); }
 @Test void consultaInexistente() { when(repo.findById(99L)).thenReturn(Optional.empty()); assertThrows(RuntimeException.class, () -> servicio.obtenerEspecialidadPorId(99L)); }
 @Test void actualizacionInexistenteNoGuarda() { assertThrows(RuntimeException.class, () -> servicio.actualizarEspecialidad(99L,request())); verify(repo,never()).save(any()); }
 @Test void eliminaExistente() { when(repo.existsById(1L)).thenReturn(true); servicio.eliminarEspecialidad(1L); verify(repo).deleteById(1L); }
 @Test void noEliminaInexistente() { assertThrows(RuntimeException.class, () -> servicio.eliminarEspecialidad(99L)); verify(repo,never()).deleteById(anyLong()); }
 @Test void creaYDevuelveIdGenerado() { 
 when(repo.save(any())).thenAnswer(i -> { Especialidad e=i.getArgument(0); e.setIdEspecialidad(7L); return e; });
 var resultado=servicio.guardarEspecialidad(request()); assertEquals(7L,resultado.getIdEspecialidad());
 var captor=org.mockito.ArgumentCaptor.forClass(Especialidad.class); verify(repo).save(captor.capture());
assertEquals("General",resultado.getNombreEspecialidad());
}
@Test void nombreDuplicadoNoGuarda() { when(repo.findByNombreEspecialidad("General")).thenReturn(Optional.of(new Especialidad())); assertThrows(RuntimeException.class, () -> servicio.guardarEspecialidad(request())); verify(repo,never()).save(any()); }
@Test void actualizaConservandoId() {
 var e=new Especialidad(); e.setIdEspecialidad(5L); when(repo.findById(5L)).thenReturn(Optional.of(e)); 
 when(repo.save(any())).thenAnswer(i->i.getArgument(0));
 var resultado=servicio.actualizarEspecialidad(5L,request());
 assertEquals(5L,resultado.getIdEspecialidad()); verify(repo).save(e);
 }
}
