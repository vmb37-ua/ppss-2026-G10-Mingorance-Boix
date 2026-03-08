package llamadas;

public class GestorLlamadasTestable extends GestorLlamadas{
    Calendario cal;

    public void setCalendario(Calendario a){
        cal = a;
    }

    @Override
    public Calendario getCalendario(){
        return cal;
    }
}
