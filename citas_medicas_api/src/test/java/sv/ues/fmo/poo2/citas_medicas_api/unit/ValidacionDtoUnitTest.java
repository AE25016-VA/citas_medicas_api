package sv.ues.fmo.poo2.citas_medicas_api.unit;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sv.ues.fmo.poo2.citas_medicas_api.dto.request.*;
class ValidacionDtoUnitTest {
 @Test void rechazaSolicitudesVaciasDeTodosLosModulos() {
 try(var factory=Validation.buildDefaultValidatorFactory()) {
 var validator=factory.getValidator();
 for(Object dto: new Object[]{new MedicoRequestDto(),new PacienteRequestDTO(),new EspecialidadRequestDto(),new HorarioMedicoRequestDto(),new CitaRequestDTO()}) {
 assertFalse(validator.validate(dto).isEmpty(),dto.getClass().getSimpleName());
 }
 }
 }
 @Test void rechazaDuracionMenorADiezMinutos() {
 try(var factory=Validation.buildDefaultValidatorFactory()) {
 var r=new HorarioMedicoRequestDto(); r.setMedicoId(1L); r.setDiasTurno("LUNES"); r.setHoraInicio(java.time.LocalTime.of(8,0)); r.setHoraFin(java.time.LocalTime.of(12,0)); r.setDuracionConsultaMinutos(5);
 assertTrue(factory.getValidator().validate(r).stream().anyMatch(v->v.getPropertyPath().toString().equals("duracionConsultaMinutos")));
 }
 }
}
