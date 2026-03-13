package ppss;

import java.time.LocalDate;

public class NotifyCenterTestable extends NotifyCenter{
    private MailServer stub;
    LocalDate now;

    @Override
    public MailServer getServer() {
        return stub;
    }

    @Override
    public LocalDate getNow() {
        return now;
    }

    public void setServer(MailServer a){
        stub = a;
    }

    public void setNow(LocalDate now){
        this.now = now;
    }

}
