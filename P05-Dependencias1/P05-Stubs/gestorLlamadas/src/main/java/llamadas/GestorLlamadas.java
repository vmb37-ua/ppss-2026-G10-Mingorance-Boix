package llamadas;
import java.util.Calendar;
public class GestorLlamadas {
    private static final double TARIFA_NOCTURNA=6.50;
    private static final double TARIFA_DIURNA=14.70;
    public Calendario getCalendario() {
        Calendario c = new Calendario();
        return c;
    }

    public double calculaConsumo(int minutos) {
        Calendario c = getCalendario();
        int hora = c.getHoraActual();
        if(hora < 8 || hora > 20) {
            return minutos * TARIFA_NOCTURNA;
        } else {
            return minutos * TARIFA_DIURNA;
        }
    }
}