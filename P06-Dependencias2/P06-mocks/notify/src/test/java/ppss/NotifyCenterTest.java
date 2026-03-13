package ppss;

import static org.junit.jupiter.api.Assertions.*;

import org.easymock.IMocksControl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.easymock.EasyMock.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class NotifyCenterTest {
    NotifyCenter sut;
    LocalDate date;
    MailServer mail;
    IMocksControl ctrl;

    @BeforeEach
    void setup(){
        ctrl = createStrictControl();
        sut = partialMockBuilder(NotifyCenter.class).addMockedMethods("sendNotify", "getServer", "getNow").mock(ctrl);
        //date = mock(LocalDate.class);??????????????????
        mail = mock(MailServer.class);
    }

    @Test
    void C1_return_exception_when_fails_email23(){
        LocalDate hoy = LocalDate.of(2026, 3, 23);

        expect(sut.getServer()).andReturn(mail);
        expect(sut.getNow()).andReturn(hoy);

        expect(mail.findMailItemsWithDate(same(hoy))).andReturn(Arrays.asList("email1", "email2", "email3", "email4"));
        assertDoesNotThrow(()->{
            sut.sendNotify("email1");
            sut.sendNotify("email2");
            expectLastCall().andThrow(new FailedNotifyException("Failures during sending porcess"));
            sut.sendNotify("email3");
            expectLastCall().andThrow(new FailedNotifyException("Failures during sending porcess"));
            sut.sendNotify("email4");
        });

        replay(sut, mail);

        FailedNotifyException e = assertThrows(FailedNotifyException.class,()->sut.notifyUsers(hoy));
        assertEquals("Failures during sending process",e.getMessage());

        verify();
    }

    @Test
    void C2_return_date_error_when_date_future(){
        LocalDate hoy = LocalDate.of(2026, 2, 2);

        expect(sut.getServer()).andReturn(mail);
        expect(sut.getNow()).andReturn(hoy);

        replay(sut, mail);

        FailedNotifyException e = assertThrows(FailedNotifyException.class,()->sut.notifyUsers(LocalDate.of(2026,3,24)));
        assertEquals("Date error",e.getMessage());

        verify();
    }

    @Test
    void C3_return_nothing_when_all_right(){
        LocalDate hoy = LocalDate.of(2026, 3, 7);

        expect(sut.getServer()).andReturn(mail);
        expect(sut.getNow()).andReturn(hoy);
        expect(mail.findMailItemsWithDate(same(hoy))).andReturn(Arrays.asList());

        replay(sut, mail);

        assertDoesNotThrow(()->sut.notifyUsers(hoy));

        verify();
    }
}
