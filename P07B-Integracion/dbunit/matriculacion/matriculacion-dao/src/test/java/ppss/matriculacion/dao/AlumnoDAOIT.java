package ppss.matriculacion.dao;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ppss.matriculacion.dao.FactoriaDAO;
import ppss.matriculacion.dao.IAlumnoDAO;
import ppss.matriculacion.dao.MiJdbcDatabaseTester;
import ppss.matriculacion.to.AlumnoTO;

import java.time.LocalDate;

public class AlumnoDAOIT {

    private IDatabaseTester databaseTester;
    private IDatabaseConnection connection;
    private IAlumnoDAO alumnoDAO;

    @BeforeEach
    public void setUp() throws Exception {
        // Arrange
        databaseTester = new MiJdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/matriculacion?useSSL=false", "ppss_user", "ppss-2025");

        // Act
        connection = databaseTester.getConnection();
        alumnoDAO = new FactoriaDAO().getAlumnoDAO();

        // Assert (No aplica en setUp)
    }

    @Test
    public void A1_alumno_should_be_added_when_does_not_exist() throws Exception {
        // Arrange
        IDataSet dataSetInit = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSetInit);
        databaseTester.onSetup();

        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("33333333C");
        alumno.setNombre("Elena Aguirre Juarez");
        alumno.setFechaNacimiento(LocalDate.of(1985, 2, 22));

        // Act
        Assertions.assertDoesNotThrow(() -> alumnoDAO.addAlumno(alumno));

        // Assert
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla-esperada-testA1.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    @Tag("TestImportante")
    public void A2_alumno_should_throw_exception_when_already_exists() throws Exception {
        // Arrange
        IDataSet dataSetInit = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSetInit);
        databaseTester.onSetup();

        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("11111111A");
        alumno.setNombre("Alfonso Ramirez Ruiz");
        alumno.setFechaNacimiento(LocalDate.of(1982, 2, 22));

        // Act
        Exception exception = Assertions.assertThrows(Exception.class, () -> alumnoDAO.addAlumno(alumno));

        // Assert
        Assertions.assertTrue(exception.getMessage().contains("Error al conectar con BD"));
    }

    @Test
    public void A4_alumno_should_throw_exception_when_is_null() throws Exception {
        // Arrange
        IDataSet dataSetInit = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSetInit);
        databaseTester.onSetup();

        // Act
        Exception exception = Assertions.assertThrows(Exception.class, () -> alumnoDAO.addAlumno(null));

        // Assert
        Assertions.assertTrue(exception.getMessage().contains("Alumno nulo"));
    }

    @Test
    public void B1_alumno_should_be_deleted_when_exists() throws Exception {
        // Arrange
        IDataSet dataSetInit = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSetInit);
        databaseTester.onSetup();

        // Act
        Assertions.assertDoesNotThrow(() -> alumnoDAO.delAlumno("11111111A"));

        // Assert
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla-esperada-testB1.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    @Tag("TestImportante")
    public void B2_alumno_should_throw_exception_when_does_not_exist() throws Exception {
        // Arrange
        IDataSet dataSetInit = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        databaseTester.setDataSet(dataSetInit);
        databaseTester.onSetup();

        // Act
        Exception exception = Assertions.assertThrows(Exception.class, () -> alumnoDAO.delAlumno("333333333333C"));

        // Assert
        Assertions.assertTrue(exception.getMessage().contains("No se ha borrado ningun alumno"));
    }
}