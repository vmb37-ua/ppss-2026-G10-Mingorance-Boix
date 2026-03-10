package ppss;

public class MatriculaAlumnoTestable extends MatriculaAlumno{

    Operacion opc;

    public void setOperacion(Operacion o){
        opc = o;
    }

    @Override
    protected Operacion getOperacion(){
        return opc;
    }
}
