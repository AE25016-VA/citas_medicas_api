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
class MedicoUnitTest {
 MedicoRepository repo = mock(MedicoRepository.class);
 EspecialidadRepository especialidades = mock(EspecialidadRepository.class);
 MedicoServiceImpl servicio = new MedicoServiceImpl(repo, especialidades);
 MedicoRequestDto request() { MedicoRequestDto r = new MedicoRequestDto(); r.setNombreMedico("Ana"); r.setApellidoMedico("Prueba"); r.setCorreoMedico("ana@example.com"); r.setNombreUsuario("ana"); r.setContrasena("Prueba123"); r.setGeneroMedico("Femenino"); return r; }
 @Test void listaVacia() { when(repo.findAll()).thenReturn(List.of()); assertTrue(servicio.listarMedicos().isEmpty()); }
 @Test void consultaInexistente() { when(repo.findById(99L)).thenReturn(Optional.empty()); assertThrows(RuntimeException.class, () -> servicio.obtenerMedicoPorId(99L)); }
 @Test void actualizacionInexistenteNoGuarda() { assertThrows(RuntimeException.class, () -> servicio.actualizarMedico(99L,request())); verify(repo,never()).save(any()); }
 @Test void eliminaExistente() { when(repo.existsById(1L)).thenReturn(true); servicio.eliminarMedico(1L); verify(repo).deleteById(1L); }
 @Test void noEliminaInexistente() { assertThrows(RuntimeException.class, () -> servicio.eliminarMedico(99L)); verify(repo,never()).deleteById(anyLong()); }
 @Test void creaYDevuelveIdGenerado() { 
 when(repo.save(any())).thenAnswer(i -> { Medico e=i.getArgument(0); e.setIdMedico(7L); return e; });
 var resultado=servicio.guardarMedico(request()); assertEquals(7L,resultado.getIdMedico());
 var captor=org.mockito.ArgumentCaptor.forClass(Medico.class); verify(repo).save(captor.capture());
assertEquals("ana@example.com",captor.getValue().getCorreoMedico()); assertEquals("Ana",resultado.getNombreMedico());
}
@Test void actualizaConservandoId() {
 var e=new Medico(); e.setIdMedico(5L); when(repo.findById(5L)).thenReturn(Optional.of(e)); 
 when(repo.save(any())).thenAnswer(i->i.getArgument(0));
 var resultado=servicio.actualizarMedico(5L,request());
 assertEquals(5L,resultado.getIdMedico()); verify(repo).save(e);
 }
}
