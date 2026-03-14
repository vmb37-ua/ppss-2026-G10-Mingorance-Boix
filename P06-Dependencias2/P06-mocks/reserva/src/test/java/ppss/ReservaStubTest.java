package ppss;

import static org.junit.jupiter.api.Assertions.*;
import static org.easymock.EasyMock.*;

import org.easymock.IMocksControl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

public class ReservaStubTest {

    Reserva sut;
    IOperacionBO op;
    FactoriaBOs fab;

    @BeforeEach
    void setup(){
        sut = partialMockBuilder(Reserva.class).addMockedMethod("compruebaPermisos").niceMock();
        op = niceMock(IOperacionBO.class);
        fab = niceMock(FactoriaBOs.class);
    }

    @Test
    void C1_should_exception_when_not_permission(){
        expect(sut.compruebaPermisos("xxxx", "xxxx", Usuario.BIBLIOTECARIO)).andReturn(false);
        replay();

        ReservaException e = assertThrows(ReservaException.class, ()->sut.realizaReserva("xxxx", "xxxx", "Pepe", new String[]{"33333"}));
        assertEquals("ERROR de permisos; ", e.getMessage());

        verify();
    }

    @Test
    void C2_should_correct_when_all_right(){
        String socio = "Pepe";
        String ppss = "ppss";

        expect(sut.compruebaPermisos(same(ppss), same(ppss), same(Usuario.BIBLIOTECARIO))).andReturn(true);
        expect(fab.getOperacionBO()).andReturn(op);

        assertDoesNotThrow(()->{
            op.operacionReserva(socio,"22222");
            op.operacionReserva(socio,"33333");
        });

        replay(sut, fab, op);

        sut.setFactoria(fab);
        assertDoesNotThrow(()->sut.realizaReserva(ppss, ppss, socio, new String[]{"22222", "33333"}));

        verify(sut, fab, op);
    }

    @Test
    void C3_should_exception_when_not_existing_isbn(){
        String socio = "Pepe";
        String ppss = "ppss";

        expect(sut.compruebaPermisos(same(ppss), same(ppss), same(Usuario.BIBLIOTECARIO))).andReturn(true);
        expect(fab.getOperacionBO()).andReturn(op);

        assertDoesNotThrow(()->{
            op.operacionReserva(socio,"11111");
            expectLastCall().andThrow(new IsbnInvalidoException());
            op.operacionReserva(socio,"22222");
            op.operacionReserva(socio,"55555");
            expectLastCall().andThrow(new IsbnInvalidoException());
        });

        replay(sut, fab, op);

        sut.setFactoria(fab);
        ReservaException e = assertThrows(ReservaException.class, ()->sut.realizaReserva(ppss, ppss, socio, new String[]{ "11111", "22222", "55555"}));
        assertEquals("ISBN invalido:11111; ISBN invalido:55555; ", e.getMessage());

        verify(sut, fab, op);
    }

    @Test
    void C4_should_exception_when_not_valid_associate(){
        String socio = "Luis";
        String ppss = "ppss";

        expect(sut.compruebaPermisos(same(ppss), same(ppss), same(Usuario.BIBLIOTECARIO))).andReturn(true);
        expect(fab.getOperacionBO()).andReturn(op);

        assertDoesNotThrow(()->{
            op.operacionReserva(socio,"22222");
            expectLastCall().andThrow(new SocioInvalidoException());
        });

        replay(sut, fab, op);

        sut.setFactoria(fab);
        ReservaException e = assertThrows(ReservaException.class, ()->sut.realizaReserva(ppss, ppss, socio, new String[]{"22222"}));
        assertEquals("SOCIO invalido; ", e.getMessage());

        verify(sut, fab, op);
    }

    @Test
    void C5_should_exception_when_conn_failed(){
        String socio = "Pepe";
        String ppss = "ppss";

        expect(sut.compruebaPermisos(same(ppss), same(ppss), same(Usuario.BIBLIOTECARIO))).andReturn(true);
        expect(fab.getOperacionBO()).andReturn(op);

        assertDoesNotThrow(()->{
            op.operacionReserva(socio,"11111");
            expectLastCall().andThrow(new IsbnInvalidoException());
            op.operacionReserva(socio,"22222");
            op.operacionReserva(socio,"33333");
            expectLastCall().andThrow(new JDBCException());
        });

        replay(sut, fab, op);

        sut.setFactoria(fab);
        ReservaException e = assertThrows(ReservaException.class, ()->sut.realizaReserva(ppss, ppss, socio, new String[]{ "11111", "22222", "33333"}));
        assertEquals("ISBN invalido:11111; CONEXION invalida; ", e.getMessage());

        verify(sut, fab, op);
    }
}

