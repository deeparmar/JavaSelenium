package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;


public class TestCalculatorPage  {

    public static ChromeOptions options;
    public static WebDriver driver ;

    @DataProvider
    public static Object[][] interestCalculationTestData() {
        return new Object[][] {
                //amount, rate, duration,expectedInterest, expectedTotalamount
                {"100000","5","Yearly","Interest Amount: 5000.00","Total Amount with Interest: 105000.00"},
                {"100000","5","Monthly","Interest Amount: 416.67","Total Amount with Interest: 100416.67"},
                {"100000","5","Daily","Interest Amount: 13.70","Total Amount with Interest: 100013.70"},
                {"100000","15","Yearly","Interest Amount: 15000.00","Total Amount with Interest: 115000.00"}

        };
    }

   @Test(dataProvider = "interestCalculationTestData")
    void test_calculate_interest (String amount,String rate, String duration, String expectedInterest, String expectedTotalAmount)  throws InterruptedException, SQLException {
        System.out.println("amount is:" + amount);
        System.out.println("interest rate is:" + rate);
        System.out.println("duration is:" + duration);
        System.out.println("expected calculated interest is:" + expectedInterest);
        System.out.println("expected total amount  is:" + expectedTotalAmount);

        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        driver = new ChromeDriver(options);
        driver.get("https://tenteststorage.z33.web.core.windows.net/index.html");

        driver.manage().window().maximize();
        //Enter Amount
        WebElement amountTextBox = driver.findElement(By.xpath("//*[@id=\"amount\"]"));
        amountTextBox.sendKeys(amount);
        //Enter Rate
        WebElement intDropdown = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/div/button"));
        Actions actions = new Actions(driver);
        actions.moveToElement(intDropdown).perform();

        WebElement chkBox = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/div/div/label[" + rate +"]/input"));
        actions.moveToElement(chkBox).click().perform();

        //Enter Duration for calaulating interest
        Select durationInput = new Select(driver.findElement(By.name("duration")));
        durationInput.selectByVisibleText(duration);

        //Click on Calculate button
        WebElement e = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/button"));
        actions.moveToElement(e).click().perform();
        e.click();

        //Assert the results
        String calculatedInterest = driver.findElement(By.xpath("//*[@id=\"interestAmount\"]")).getText();
        String totalAmount = driver.findElement(By.xpath("//*[@id=\"totalAmount\"]")).getText();

        Assert.assertEquals(calculatedInterest, expectedInterest);
        Assert.assertEquals(totalAmount, expectedTotalAmount);

        //****************Mandatory field validation Alertbox***************************
        //Clear out amount box
//        amountTextBox.clear();
//
//        actions.moveToElement(e).click().perform();
//        e.click();
//
//       // driver.wait(5000);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.alertIsPresent());
//        String expectedAlertText ="Please fill in all fields";
//        Alert alert = driver.switchTo().alert();
//        String alertText = alert.getText();
//
//        //Assert alertbox validation message
//        Assert.assertEquals(expectedAlertText, alertText);

        driver.quit();
      }

    @AfterTest
    public void close_browser()
    {
      driver.quit();
    }
}
