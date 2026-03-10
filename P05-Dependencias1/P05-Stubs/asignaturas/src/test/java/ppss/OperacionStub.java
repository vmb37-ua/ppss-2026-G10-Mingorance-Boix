package ppss;

import java.util.ArrayList;
import java.util.Queue;

public class OperacionStub extends Operacion {
    ArrayList<Integer> res;

    public OperacionStub(ArrayList<Integer> res){this.res = res;}

    @Override
    public void compruebaMatricula(String dni, String asig) throws AsignaturaIncorrectaException, AsignaturaCursadaException{
        Integer opc = res.get(0);
        res.remove(0);

        switch (opc){
            case 0: throw new AsignaturaIncorrectaException();
            case 1: throw new AsignaturaCursadaException();
        }
    }
}
