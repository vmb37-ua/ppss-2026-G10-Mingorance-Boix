package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;

import java.util.ArrayList;
import java.util.Arrays;

public class OperacionStub implements IOperacionBO{

    ArrayList<String> isbns = new ArrayList<>(Arrays.asList("11111","22222"));
    int contador;

    public OperacionStub(int a){
        contador = a;
    }

    @Override
    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, JDBCException, SocioInvalidoException {
        if(!socio.equals("Luis")){
            throw new SocioInvalidoException();
        }
        if(!isbns.contains(isbn)){
            throw new IsbnInvalidoException();
        }

        contador--;
        if (contador == 0){
            throw new JDBCException();
        }
    }
}
