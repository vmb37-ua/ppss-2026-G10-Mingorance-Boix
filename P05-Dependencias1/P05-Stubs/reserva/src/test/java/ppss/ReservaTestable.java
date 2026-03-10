package ppss;

public class ReservaTestable extends Reserva{

    int error = 0;

    public void setError(int n){
        error = n;
    }

    @Override
    public IOperacionBO getOperacion(){
        return new OperacionStub(error);
    }

    @Override
    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu){
        if(login.equals("ppss") && password.equals("ppss") && tipoUsu == Usuario.BIBLIOTECARIO){
            return true;
        }
        return false;
    }

}
