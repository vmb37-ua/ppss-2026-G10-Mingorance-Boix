package llamadas;

public class CalendarioStub extends Calendario{
    private int hora;
    public CalendarioStub(int h){
        hora = h;
    }
    @Override
    public int getHoraActual(){
        return hora;
    }
}
