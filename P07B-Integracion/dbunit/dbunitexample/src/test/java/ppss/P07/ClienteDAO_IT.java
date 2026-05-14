 package ppss.P07;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;

/* IMPORTANTE:
    Dado que prácticamente todos los métodos de dBUnit lanzan una excepción,
    vamos a usar "throws Esception" en los métodos, para que el código quede más
    legible sin necesidad de usar un try..catch o envolver cada sentencia dbUnit 
    con un assertDoesNotThrow()
    Es decir, que vamos a primar la legibilidad de los tests.
    Si la SUT puede lanza una excepción, SIEMPRE usaremos assertDoesNotThrow para
    invocar a la sut cuando no esperemos que se lance dicha excepción (independientemente de que hayamos propagado las excepciones provocadas por dbunit).
*/
public class ClienteDAO_IT {
  
  private ClienteDAO clienteDAO; //SUT
  private IDatabaseTester databaseTester;
  private IDatabaseConnection connection;

  @BeforeEach
  public void setUp() throws Exception {

    String cadena_conexionDB = "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false";
    databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
            cadena_conexionDB, "ppss_user", "ppss-2025");
    connection = databaseTester.getConnection();

    clienteDAO = new ClienteDAO();
  }

  @Test
  public void D1_insert_should_add_John_to_cliente_when_John_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-vacia.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
    
     //invocamos a la sut
    Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente"); 

    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);

   }

  @Test
  public void D2_delete_should_remove_John_from_cliente_when_John_is_in_table() throws Exception {
    Cliente cliente =  new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la SUT
    Assertions.assertDoesNotThrow(()->clienteDAO.delete(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");
    
    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-vacia.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D3_insert_duplicate_should_throw_exception() throws Exception {
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-dos-registros.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    Cliente clienteDuplicado = new Cliente(1, "John", "Smith");

    Exception exception = Assertions.assertThrows(Exception.class, () -> {
      clienteDAO.insert(clienteDuplicado);
    });

    Assertions.assertTrue(exception.getMessage().contains("Duplicate entry"),
            "El mensaje de error debería contener 'Duplicate entry'");
  }

  @Test
  public void D4_delete_non_existent_should_throw_exception() throws Exception {
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-dos-registros.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    Cliente clienteInexistente = new Cliente(99, "Fantasma", "Perez");

    Exception exception = Assertions.assertThrows(Exception.class, () -> {
      clienteDAO.delete(clienteInexistente);
    });

    Assertions.assertTrue(exception.getMessage().contains("Delete failed!"),
            "El mensaje de error debería contener 'Delete failed!'");
  }

  @Test
  public void D5_update_should_modify_Johns_address_and_city() throws Exception {
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    Cliente clienteModificado = new Cliente(1, "John", "Smith");
    clienteModificado.setDireccion("Other Street");
    clienteModificado.setCiudad("NewCity");

    Assertions.assertDoesNotThrow(() -> clienteDAO.update(clienteModificado));

    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");

    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado-update.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D6_retrieve_should_return_Johns_data() throws Exception {
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-un-registro.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    Cliente clienteRecuperado = clienteDAO.retrieve(1);

    Assertions.assertAll(()->{
      Assertions.assertNotNull(clienteRecuperado, "El cliente recuperado no debería ser nulo");
      Assertions.assertEquals(1, clienteRecuperado.getId());
      Assertions.assertEquals("John", clienteRecuperado.getNombre());
      Assertions.assertEquals("Smith", clienteRecuperado.getApellido());
      Assertions.assertEquals("1 Main Street", clienteRecuperado.getDireccion());
      Assertions.assertEquals("Anycity", clienteRecuperado.getCiudad());});

  }

}
