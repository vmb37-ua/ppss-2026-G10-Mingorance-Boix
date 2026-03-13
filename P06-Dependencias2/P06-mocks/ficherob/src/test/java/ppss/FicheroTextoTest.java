package ppss;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import static org.easymock.EasyMock.*;

import java.io.FileReader;
import java.io.IOException;

public class FicheroTextoTest {
    FicheroTextoTestable sut;

    @BeforeEach
    void setup(){
        sut = new FicheroTextoTestable();
    }

    @Test
    void C1_should_return_exception_when_cant_read_file(){
        FileReader mock = mock(FileReader.class);
        assertDoesNotThrow(()->expect(mock.read()).andReturn((int)'a').andReturn((int)'b').andThrow(new IOException()));
        //assertDoesNotThrow(()->expect(mock.close()).andVoid()); ????????????????????????
        replay(mock);
        sut.setStub(mock);
        FicheroException e;

        e = assertThrows(FicheroException.class, ()->sut.contarCaracteres("src/test/resources/ficheroC1.txt"));
        assertEquals("src/test/resources/ficheroC1.txt (Error al leer el archivo)", e.getMessage());

        verify(mock);

    }

    @Test
    void C2_should_exception_when_cant_close(){
        FileReader mock = mock(FileReader.class);
        assertDoesNotThrow(()->expect(mock.read()).andReturn((int)'a').andReturn((int)'b').andReturn((int)'c').andReturn(-1));
        assertDoesNotThrow(()->mock.close());
        expectLastCall().andThrow(new IOException());
        replay(mock);
        sut.setStub(mock);
        FicheroException e;

        e = assertThrows(FicheroException.class, ()->sut.contarCaracteres("src/test/resources/ficheroC2.txt"));
        assertEquals("src/test/resources/ficheroC2.txt (Error al cerrar el archivo)", e.getMessage());

        verify(mock);
    }
}
