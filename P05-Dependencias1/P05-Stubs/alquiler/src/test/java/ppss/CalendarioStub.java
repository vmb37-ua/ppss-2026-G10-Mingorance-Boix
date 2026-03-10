package ppss;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarioStub extends Calendario {
    private ArrayList<LocalDate> fiestas;
    private ArrayList<LocalDate> errores;
    public CalendarioStub(ArrayList<LocalDate> fiestas, ArrayList<LocalDate> errores){
        this.fiestas = fiestas;
        this.errores = errores;
    }

    @Override
    public boolean es_festivo(LocalDate dia) throws CalendarioException{
        if(fiestas.contains(dia)) return true;
        if(errores.contains(dia)) throw new CalendarioException();
        return false;
    }
}
