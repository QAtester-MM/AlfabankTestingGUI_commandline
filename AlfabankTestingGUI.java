package org.example;

        import io.qameta.allure.junit4.DisplayName;
        import org.junit.*;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.chrome.ChromeDriver;
        import static com.codeborne.selenide.Selenide.*;
        import static com.codeborne.selenide.Condition.*;

        import static org.junit.Assert.assertEquals;

/**
 * Unit test for Alfabank.
 */
public class AlfabankTestingGUI
{
    private static WebDriver driver;
    private static WebElement eEntry;
    private static String cardNumber1string;
    private static String cardNumber2string;
    private static String cvcNumberstring;
    private static String inputDatestring;
    private static String endPointstring;
    private static String transferSummstring;


    // Global precondition - before all test
    @BeforeClass
    public static void createInstance() throws InterruptedException {

        long cardNumber1 = Long.valueOf( System.getProperty("num1") );
        cardNumber1string = System.getProperty("num1");
        System.out.println( "Reading config file : " + cardNumber1 );

        long cardNumber2 = Long.valueOf( System.getProperty("num2") );
        cardNumber2string = System.getProperty("num2");
        System.out.println( "Reading config file : " + cardNumber2 );

        int inputDate = Integer.valueOf( System.getProperty("date") );
        inputDatestring = System.getProperty("date");
        System.out.println( "Reading config file : " + inputDate );

        int cvcNumber = Integer.valueOf( System.getProperty("cvc") );
        cvcNumberstring = System.getProperty("cvc");
        System.out.println( "Reading config file : " + cvcNumber );

        String endPoint = String.valueOf( System.getProperty("url") );
        endPointstring = System.getProperty("url");
        System.out.println( "Reading config file : " + endPoint );

        int transferSumm = Integer.valueOf( System.getProperty("summ") );
        transferSummstring = System.getProperty("summ");
        System.out.println( "Reading config file : " + transferSumm );

//!!!! FIX your path
        System.setProperty("webdriver.chrome.driver", "//Users/imac/Downloads/chromedriver");

//Initialize browser
        driver = new ChromeDriver();
        System.out.println("Instance created");
    }

// After condition - after every test
    @AfterClass
    public static void deleteInstance( ) throws InterruptedException {
        driver.close();
        driver.quit();
        System.out.println("Instance deleted");
    }
// precondition - before each test
    @Before
    public void prepare() throws InterruptedException {

        driver.get(endPointstring);
        Thread.sleep(500);
        eEntry = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[1]/div/div[3]/div/div/p/a"));
        eEntry.click();
    }

    @Test
    @DisplayName("ТЕСТ 1")
    public void wrongCardNumber( ) throws InterruptedException {
    // в поле ввода 1 ...
        eEntry = driver.findElement(By.xpath("/html/body/div/form/div[2]/div[1]/div[1]/div[3]/div[1]/input[2]"));
    // ... вводим значение (16-ти значный номер карты)
        eEntry.sendKeys(cardNumber1string);
        Thread.sleep(500);
    // в полле MM/YY вводим дату
        eEntry = driver.findElement(By.xpath("/html/body/div/form/div[2]/div[1]/div[1]/div[3]/div[2]/input"));
        eEntry.sendKeys(inputDatestring);
    // в поле CVC вводим cvc
        eEntry = driver.findElement(By.xpath("/html/body/div/form/div[2]/div[1]/div[1]/div[3]/div[3]/input"));
        eEntry.sendKeys(cvcNumberstring);
    // указываем адрес элемента с надписью   «проблемы с картой отправителя»
        WebElement wrongSenderCard = driver.findElement(By.xpath("/html/body/div/form/div[4]/div[2]" ));
        System.out.println( wrongSenderCard.getText() );
        Thread.sleep(500);
    // проверка надписи 2
        assertEquals("Неправильный номер карты отправителя", wrongSenderCard.getText());
    }

    @Test
    @DisplayName("ТЕСТ 2")
    public void c2c( ) throws InterruptedException {

        // в поле ввода 1 вводим значение (16-ти значный номер карты)
        eEntry = driver.findElement(By.xpath("/html/body/div/form/div[2]/div[1]/div[1]/div[3]/div[1]/input[2]"));
        eEntry.sendKeys(cardNumber1string);
        Thread.sleep(500);
        // в поле MM/YY вводим дату
        eEntry = driver.findElement(By.xpath("/html/body/div/form/div[2]/div[1]/div[1]/div[3]/div[2]/input"));
        eEntry.sendKeys(inputDatestring);
        // CVC
        eEntry = driver.findElement(By.xpath("/html/body/div/form/div[2]/div[1]/div[1]/div[3]/div[3]/input"));
        eEntry.sendKeys(cvcNumberstring);
        // в поле ввода 2 вводим номер карты 2
        eEntry = driver.findElement(By.xpath("/html/body/div/form/div[2]/div[2]/div[1]/div[3]/div/input[2]"));
        eEntry.sendKeys(cardNumber2string);
        // в поле «сумма» вводим сумму
        eEntry = driver.findElement(By.xpath("/html/body/div/form/div[5]/input[2]"));
        eEntry.sendKeys(transferSummstring);
        // указываем адрес элемента с надписью   «проблемы с картой отправителя»
        WebElement wrongSenderCard = driver.findElement(By.xpath("/html/body/div/form/div[4]/div[2]" ));
        System.out.println( wrongSenderCard.getText() );
        Thread.sleep(500);
        // проверка надписи 2
        assertEquals("Неправильный номер карты отправителя", wrongSenderCard.getText());
    }

}
