package ppss;

import static org.junit.jupiter.api.Assertions.*;
import static ppss.TipoCoche.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class AlquilaCochesTest {
    AlquilaCochesTestable sut;

    @BeforeEach
    void setup(){
        sut = new AlquilaCochesTestable();
    }

    @Test
    void C1_should_not_upcount_when_not_festivities(){
        ArrayList<LocalDate> fiestas = new ArrayList<>();
        ArrayList<LocalDate> errores = new ArrayList<>();
        int dias = 10;
        LocalDate inicio = LocalDate.of(2024, 05, 18);
        TipoCoche tipo = TURISMO;
        sut.setCalendario(new CalendarioStub(fiestas, errores));
        Ticket esperado = new Ticket();
        esperado.setPrecio_final(75);
        sut.setServicio(new ServicioStub());

        Ticket real = new Ticket();
        try {
            real = sut.calculaPrecio(tipo, inicio, dias);
        }catch(MensajeException e){
        }

        assertEquals(esperado.getPrecio(), real.getPrecio());
    }

    @Test
    void C2_should_parcially_discount_when_some_festivities(){
        ArrayList<LocalDate> fiestas = new ArrayList<>(Arrays.asList(LocalDate.of(2024,06,20), LocalDate.of(2024,06,24)));
        ArrayList<LocalDate> errores = new ArrayList<>();
        int dias = 7;
        LocalDate inicio = LocalDate.of(2024, 06, 19);
        TipoCoche tipo = CARAVANA;
        sut.setCalendario(new CalendarioStub(fiestas, errores));
        Ticket esperado = new Ticket();
        esperado.setPrecio_final(62.5f);
        sut.setServicio(new ServicioStub());

        Ticket real = new Ticket();
        try {
            real = sut.calculaPrecio(tipo, inicio, dias);
        }catch(MensajeException e){
        }

        assertEquals(esperado.getPrecio(), real.getPrecio());
    }

    @Test
    void C3_should_error_when_error_in_days(){
        ArrayList<LocalDate> fiestas = new ArrayList<>();
        ArrayList<LocalDate> errores = new ArrayList<>(Arrays.asList(LocalDate.of(2024,04,18),
                LocalDate.of(2024,04,21),
                LocalDate.of(2024,04,22)));
        int dias = 8;
        LocalDate inicio = LocalDate.of(2024, 04, 17);
        TipoCoche tipo = TURISMO;
        sut.setCalendario(new CalendarioStub(fiestas, errores));
        MensajeException esperado = new MensajeException("Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ");
        sut.setServicio(new ServicioStub());

        MensajeException real = assertThrows(MensajeException.class, () -> sut.calculaPrecio(tipo, inicio, dias));
        assertEquals(esperado.observaciones, real.observaciones);
    }
}
