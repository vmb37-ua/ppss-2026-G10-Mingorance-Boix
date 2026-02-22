package ppss;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Tag;

import java.rmi.UnexpectedException;
import java.util.stream.Stream;

@DisplayName("Test asociados a la clase Cine")
class CineTest {

    private Cine sut;

    @BeforeEach
    void preparacion(){
        sut = new Cine();
    }

    @Test
    @Tag("no_parametrizado")
    void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3(){
        boolean[] asientos = new boolean[]{};
        int num = 3;
        ButacasException exception = assertThrows(ButacasException.class , ()->sut.reservaButacas(asientos, num));

        assertEquals("No se puede procesar la solicitud", exception.getMessage());
    }

    @Test
    @Tag("no_parametrizado")
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero(){
        boolean[] asientos = new boolean[]{};
        int num = 0;
        boolean[] esperado_asiento = new boolean[]{};
        try {
            boolean resultado = sut.reservaButacas(asientos, num);
        }
        catch(ButacasException e){
            fail("Excepcion no esperada");
        }

        assertAll(()->assertFalse(sut.reservaButacas(asientos, num)),
                ()->assertArrayEquals(esperado_asiento, asientos));
    }

    @Test
    @Tag("no_parametrizado")
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2(){
        boolean[] asientos = new boolean[]{false, false, false, true, true};
        boolean[] esperado_asiento = new boolean[]{true, true, false, true, true};
        int num = 2;

        assertAll(()->assertTrue(sut.reservaButacas(asientos, num)),
                ()->assertArrayEquals(esperado_asiento, asientos));
    }

    @Test
    @Tag("no_parametrizado")
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1(){
        boolean[] asientos = new boolean[]{true, true, true};
        int num = 1;
        boolean[] esperado_asiento = new boolean[]{true, true, true};

        assertAll(()->assertFalse(sut.reservaButacas(asientos, num)),
                ()->assertArrayEquals(esperado_asiento, asientos));
    }

    @ParameterizedTest(name = "reservaButacas_[{index}] should be {0} when we want {1} and {2}")
    @MethodSource("casosDePruebaC5")
    @DisplayName("reservaButacas_")
    @Tag("parametrizado")
    void C5_reservaButacas(boolean esperado, int num, String msg, boolean[] asientos, boolean[] esp_asientos){
        assertAll(()->assertEquals(esperado, sut.reservaButacas(asientos, num)),
                ()->assertArrayEquals(esp_asientos, asientos));
    }

    private static Stream<Arguments> casosDePruebaC5(){
        return Stream.of(
                Arguments.of(false, 0, "fila has no seats", new boolean[]{}, new boolean[]{}),
                        Arguments.of(true, 2, "there are 2 free seats", new boolean[]{false, false, false, true, true}, new boolean[]{true, true, false, true, true}),
                        Arguments.of(false, 1, "all seats are alredy reserved", new boolean[]{true, true, true}, new boolean[]{true, true, true})
        );
    }

    @ParameterizedTest(name = "reservaButacas_[{index}] should be {0} when we want {1} and {2}")
    @MethodSource("casosDePruebaC6")
    @DisplayName("reservaButacas_")
    @Tag("tablaB")
    @Tag("parametrizado")
    void C6_reservaButacas(boolean esperado, int num, String msg, boolean[] asientos, boolean[] esp_asientos){
        assertAll(()->assertEquals(esperado, sut.reservaButacas(asientos, num)),
                ()->assertArrayEquals(esp_asientos, asientos));
    }

    private static Stream<Arguments> casosDePruebaC6(){
        return Stream.of(
                Arguments.of(true, 1, "there are 3 free seats", new boolean[]{false,false,false}, new boolean[]{true, false, false}),
                Arguments.of(false, 1, "there are no free seats", new boolean[]{true}, new boolean[]{true})
        );
    }

}