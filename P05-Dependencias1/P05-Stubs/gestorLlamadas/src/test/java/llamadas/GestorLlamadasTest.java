package llamadas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestorLlamadasTest {
    private GestorLlamadasTestable sut;

    @BeforeEach
    void setup(){
        sut = new GestorLlamadasTestable();
    }

    @Test
    void C1_Gestor_should_return_147_when_15_10(){
        sut.setCalendario(new CalendarioStub(15));

        double real = sut.calculaConsumo(10);

        assertEquals(147,real);
    }

    @Test
    void C2_Gestor_should_return_65_when_23_10(){
        sut.setCalendario(new CalendarioStub(23));

        double real = sut.calculaConsumo(10);

        assertEquals(65,real);
    }
}
