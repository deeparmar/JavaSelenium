package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CalculaterPage {

    WebDriver driver;
    ChromeOptions options;

    public static String alertText;

    public void launchBrowser()
    {
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://tenteststorage.z33.web.core.windows.net/index.html");
        driver.manage().window().maximize();
    }

    public String getAlertText()
    {
        return alertText;
    }

    public void enterAmount(String  amount)
    {
        WebElement amountTextBox = driver.findElement(By.id("amount"));
        amountTextBox.clear();
        amountTextBox.sendKeys(amount);

    }

    public void clearAmount()
    {
        WebElement amountTextBox = driver.findElement(By.id("amount"));
        amountTextBox.clear();
    }

    public void selectRate(String rate)
    {
        WebElement intDropdown = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/div/button"));
        intDropdown.click();

        WebElement chkBox = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/div/div/label[" + rate +"]/input"));
        chkBox.click();

    }

    public void selectDuration(String duration)
    {
       //Enter Duration for calaulating interest
        WebElement durationSelect = driver.findElement(By.name("duration"));
        Select durationInput = new Select(durationSelect);
        durationInput.selectByVisibleText(duration);

    }

    public void clickCalculateButton()
    {
        Actions actions = new Actions(driver);
        WebElement calculateButton = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/button"));
        actions.moveToElement(calculateButton).click().perform();
        calculateButton.click();
    }

    public String clickCalculateButtonForValidation() {

        Actions actions = new Actions(driver);
        WebElement calculateButton = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/button"));
        actions.moveToElement(calculateButton).click().perform();
        // driver.wait(5000);
        try {
            calculateButton.click();
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
        }catch(UnhandledAlertException alertException){
           alertText = alertException.getRawMessage().toString();
           System.out.println(alertText);
        }
        return  alertText;
      //  return alertText;
    }
    public String getCalculatedInterest()
    {
        WebElement calculatedInterestText = driver.findElement(By.id("interestAmount"));
        return calculatedInterestText.getText();

    }

    public String getTotalAmount()
    {

        WebElement totalAmountText = driver.findElement(By.id("totalAmount"));
        return totalAmountText.getText();

    }

    public boolean isElementDisplayed(WebElement elementToCheck, Duration timeToWaitInSeconds, boolean catchException )
    {
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeToWaitInSeconds);
        boolean b = false;
        try{
            webDriverWait.until((ExpectedCondition<Boolean>) wd -> elementToCheck.isDisplayed());
            b = true;
        }
        catch(Exception e) {
            if(catchException == true)
            {
                e.printStackTrace();
            }
        }

        return b;

    }

    public void closeBrowser()
    {
       this.driver.quit();
    }
}
