package sv.ues.fmo.poo2.citas_medicas_api.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}