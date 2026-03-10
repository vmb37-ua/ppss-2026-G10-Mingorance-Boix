package ppss;

public class MensajeException extends Exception {
    String observaciones;
    public MensajeException(String observaciones) {
        this.observaciones = observaciones;
    }
}
