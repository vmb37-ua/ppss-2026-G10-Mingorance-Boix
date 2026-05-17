package ejercicio3.conPOyPFact;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class Cookies {

    //guarda las cookies en un fichero en el directorio target
    public static void storeCookiesToFile(String urlInicial, String login, String password, String nombre_fichero) {
        ChromeOptions co = new ChromeOptions();
        co.setExperimentalOption("excludeSwitches", java.util.Arrays.asList("enable-automation"));
        co.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);
        co.addArguments("--incognito");
        co.addArguments("--disable-notifications");
        co.addArguments("--disable-save-password-bubble");
        co.addArguments("--safebrowsing-disable-auto-update");
        WebDriver driver = new ChromeDriver(co);

        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.get(urlInicial);

            //Seleccionamos Sign In
            driver.findElement(By.linkText("Sign In")).click();

            //introducimos login y password
            WebElement login_box = driver.findElement(By.id("username"));
            login_box.clear();
            login_box.sendKeys(login);

            WebElement password_box = driver.findElement(By.id("password"));
            password_box.clear();
            password_box.sendKeys(password);

            // enviamos los datos
            WebElement loginButton = driver.findElement(By.cssSelector("button.btn.btn-primary"));
            Actions actions = new Actions(driver);
            actions.moveToElement(loginButton).click().perform();

            // Le damos al servidor 3 segundos para procesar
            // el login y devolver la cookie buena (el JSESSIONID válido)
            Thread.sleep(3000);

            //creamos el fichero donde guardaremos las cookies generadas
            File file = new File("./target/" + nombre_fichero);

            // borramos el fichero si ya existe
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter bwrite = new BufferedWriter(fileWrite);

            // bucle para obtener las cookies YA LOGUEADAS
            for(Cookie ck : driver.manage().getCookies()) {
                bwrite.write((ck.getName()+";"+ck.getValue()+";"
                        +ck.getDomain()+";"+ck.getPath()+";"
                        +ck.getExpiry()+";"+ck.isSecure()));
                bwrite.newLine();
            }
            bwrite.flush();
            bwrite.close();
            fileWrite.close();

        } catch(Exception ex) {
            System.err.println("Error al guardar las cookies: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Aseguramos que el navegador siempre se cierra
            driver.quit();
        }
    }

    //lee el fichero de cookies generado y guarda las cookies en el navegador
    public static void loadCookiesFromFile(WebDriver driver, String urlInicial, String nombre_fichero){

        //necesitamos acceder a la página principal para fijar el dominio
        driver.get(urlInicial);
        //borramos todas ls cookies de este dominio
        driver.manage().deleteAllCookies();

        //leemos los datos del fichero y los "guardamos" en el navegador
        try{
            File file = new File("./target/"+nombre_fichero);
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;

            while((strline = Buffreader.readLine()) != null){
                // Usamos split, que es 1000 veces más seguro que StringTokenizer
                String[] tokens = strline.split(";", -1);

                if(tokens.length >= 6) {
                    String name = tokens[0];
                    String value = tokens[1];
                    // Si el dominio se guardó como "null", le pasamos un null real a Selenium
                    String domain = tokens[2].equals("null") ? null : tokens[2];
                    String path = tokens[3].equals("null") ? null : tokens[3];

                    Date expiry = null;
                    if(!tokens[4].equals("null")) {
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                        expiry = formatter.parse(tokens[4]);
                    }

                    Boolean isSecure = Boolean.valueOf(tokens[5]);

                    // Creamos la cookie y la inyectamos en el navegador
                    Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                    driver.manage().addCookie(ck);
                }
            }
            Buffreader.close();

            // RECARGAMOS LA PÁGINA PARA ENVIAR LA COOKIE AL SERVIDOR
            driver.navigate().refresh();

        }catch(Exception ex){
            System.err.println("¡ERROR AL CARGAR COOKIES! " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //muestra los valores de las cookies almacenadas en el navegador
    public static void printCookies(WebDriver driver){
        System.out.println("**************** ");
        System.out.println("Datos de las cookies:");
        int i= 0;
        for(Cookie ck : driver.manage().getCookies()) {
            i++;
            System.out.println("Cookie  número "+i);
            System.out.println("   nombre: "+ ck.getName());
            System.out.println("   valor: "+ ck.getValue());
            System.out.println("   dominio: "+ ck.getDomain());
            System.out.println("   path: "+ ck.getPath());
            System.out.println("   expira: "+ ck.getExpiry());
            System.out.println("   segura: "+ ck.isSecure());
        }
        System.out.println("**************** FIN printCookies");
    }
}
