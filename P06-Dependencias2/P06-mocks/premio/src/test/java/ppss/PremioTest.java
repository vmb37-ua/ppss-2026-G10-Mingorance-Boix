package ppss;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

import static org.easymock.EasyMock.*;

public class PremioTest {

    Premio sut;

    @BeforeEach
    void setup(){
        sut = new Premio();
    }

    @Test
    void C1_should_win_champions_ticket_when_0_07(){
        ClienteWebService m = mock(ClienteWebService.class);
        assertDoesNotThrow(()->expect(m.obtenerPremio()).andReturn("entrada final Champions"));
        replay(m);
        sut.setCliente(m);
        Random a = mock(Random.class);
        assertDoesNotThrow(()->expect(a.nextFloat()).andReturn(0.07f));
        replay(a);
        sut.setGenerador(a);

        String real = sut.compruebaPremio();

        assertEquals("Premiado con entrada final Champions", real);

    }

    @Test
    void C2_when_error_returns_exception(){
        ClienteWebService m = mock(ClienteWebService.class);
        assertDoesNotThrow(()->expect(m.obtenerPremio()).andThrow(new ClienteWebServiceException()));
        replay(m);
        sut.setCliente(m);
        Random a = mock(Random.class);
        assertDoesNotThrow(()->expect(a.nextFloat()).andReturn(0.05f));
        replay(a);
        sut.setGenerador(a);

        String real = sut.compruebaPremio();

        assertEquals("No se ha podido obtener el premio", real);
    }

    @Test
    void C3_when_048_then_returns_no_prize(){
        Random a = mock(Random.class);
        assertDoesNotThrow(()->expect(a.nextFloat()).andReturn(0.48f));
        replay(a);
        sut.setGenerador(a);

        String real = sut.compruebaPremio();

        assertEquals("Sin premio", real);
    }
}
