package ppss;

public interface IOperacionBO {
    void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, JDBCException, SocioInvalidoException;
}
