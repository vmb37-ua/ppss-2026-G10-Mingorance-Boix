package ppss;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class MatriculaAlumnoTest {

    MatriculaAlumnoTestable sut;

    @BeforeEach
    void setup(){
        sut = new MatriculaAlumnoTestable();
    }

    @Test
    void C1_should_return_errors_when_not_valid(){
        Operacion opc = new OperacionStub(new ArrayList<Integer>(Arrays.asList(2,0,2,0,1)));
        sut.setOperacion(opc);
        JustificanteMatricula esperado = new JustificanteMatricula();
        esperado.setDni("00000000T");
        esperado.setErrores(new ArrayList<String>(Arrays.asList("Asignatura ZZ no existe",
                "Asignatura YYY no existe","Asignatura P1 ya cursada")));
        esperado.setAsignaturas(new ArrayList<String>(Arrays.asList("MD", "FBD")));

        JustificanteMatricula real = sut.validaAsignaturas("00000000T", new String[]{"MD", "ZZ", "FBD", "YYY", "P1"});

        assertEquals(esperado, real);
    }

    @Test
    void C2_should_not_return_errors_when_valid(){
        Operacion opc = new OperacionStub(new ArrayList<Integer>(Arrays.asList(2,2,2)));
        sut.setOperacion(opc);
        JustificanteMatricula esperado = new JustificanteMatricula();
        esperado.setDni("00000000T");
        esperado.setErrores(new ArrayList<String>());
        esperado.setAsignaturas(new ArrayList<String>(Arrays.asList("PPSS", "ADA", "P3")));

        JustificanteMatricula real = sut.validaAsignaturas("00000000T", new String[]{"PPSS", "ADA", "P3"});

        assertEquals(esperado, real);
    }
}
