package ppss;

import ppss.excepciones.ReservaException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ReservaTest {

    public ReservaTestable sut;

    @BeforeEach
    void setup(){
        sut = new ReservaTestable();
        sut.setError(-1);
    }

    @Test
    void C1_should_exception_when_not_permission(){

        ReservaException esperado = new ReservaException("ERROR de permisos; ");

        ReservaException e = assertThrows(ReservaException.class,()->sut.realizaReserva("xxxx", "xxxx", "Luis", new String[]{"11111"}));
        assertEquals(esperado.getMessage(), e.getMessage());
    }

    @Test
    void C2_should_correct_when_all_right(){
        assertDoesNotThrow(()->sut.realizaReserva("ppss", "ppss", "Luis", new String[]{"11111","22222"}));
    }

    @Test
    void C3_should_exception_when_not_existing_isbn(){
        ReservaException esperado = new ReservaException("ISBN invalido:33333; ISBN invalido:44444; ");

        ReservaException e = assertThrows(ReservaException.class,()->sut.realizaReserva("ppss", "ppss", "Luis", new String[]{"11111","33333","44444"}));
        assertEquals(esperado.getMessage(), e.getMessage());
    }

    @Test
    void C4_should_exception_when_not_valid_associate(){
        ReservaException esperado = new ReservaException("SOCIO invalido; ");

        ReservaException e = assertThrows(ReservaException.class,()->sut.realizaReserva("ppss", "ppss", "Pepe", new String[]{"11111"}));
        assertEquals(esperado.getMessage(), e.getMessage());
    }

    @Test
    void C5_should_exception_when_conn_failed(){
        sut.setError(2);
        ReservaException esperado = new ReservaException("CONEXION invalida; ");

        ReservaException e = assertThrows(ReservaException.class,()->sut.realizaReserva("ppss", "ppss", "Luis", new String[]{"11111","22222"}));
        assertEquals(esperado.getMessage(), e.getMessage());
    }
}
