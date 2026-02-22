package ppss;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class FicheroTextoTest {

    FicheroTexto sut;

    @BeforeEach
    void setup(){
        sut = new FicheroTexto();
    }

    @Test
    void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist(){
        String nombre = "resources/ficheroC1.txt";

        FicheroException e = assertThrows(FicheroException.class, ()->sut.contarCaracteres(nombre));

        assertEquals("resources/ficheroC1.txt (No existe el archivo o el directorio)", e.getMessage());
    }

    @Test
    void C2_contarCaracteres_should_return_4_when_file_has_4_chars(){
        String nombre = "../test/resources/ficheroCorrecto.txt";
        int esperado = 4;

        assertAll(()->assertEquals(esperado, sut.contarCaracteres(nombre)),
                ()->assertDoesNotThrow(()->sut.contarCaracteres(nombre)));
    }

    @Test
    @Tag("excluido")
    void C3_contarCaracteres_should_return_Exception_when_file_cannot_be_read(){
        fail();
    }

    @Test
    @Tag("excluido")
    void C4_contarCaracteres_should_return_Exception_when_file_cannot_be_closed(){
        fail();
    }
}