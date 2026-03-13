package ppss;

import java.io.FileReader;

public class FicheroTextoTestable extends FicheroTexto{

    FileReader stub;

    @Override
    public FileReader getfile(String nombre){
        return stub;
    }

    public void setStub(FileReader a){
        stub = a;
    }
}
