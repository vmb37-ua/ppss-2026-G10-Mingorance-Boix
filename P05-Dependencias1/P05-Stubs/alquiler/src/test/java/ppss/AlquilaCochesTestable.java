package ppss;

public class AlquilaCochesTestable extends AlquilaCoches{
    public Servicio serv;

    void setCalendario(Calendario cal){
        calendario = cal;
    }

    public void setServicio(Servicio otro){
        serv = otro;
    }

    @Override
    public Servicio createServicio(){
        return serv;
    }
}
