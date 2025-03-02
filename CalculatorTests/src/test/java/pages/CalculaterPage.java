package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestCalculatorPageObject {

    WebDriver driver;
    ChromeOptions options;

    @FindBy(id = "amount")
    public WebElement amountTextBox;

    @FindBy(xpath = "//*[@id=\"interestForm\"]/div/button")
    public WebElement intDropDown;

    @FindBy(name = "duration")
    public WebElement durationSelect;

    @FindBy(xpath ="//*[@id=\"interestForm\"]/button")
    public WebElement calculateButton;

    public void launchBrowser()
    {
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://tenteststorage.z33.web.core.windows.net/index.html");
        driver.manage().window().maximize();
    }

    public void enterAmount(String  amount)
    {
        if(isElementDisplayed(amountTextBox,Duration.ofSeconds(2),true))
        {
            amountTextBox.clear();
            amountTextBox.sendKeys(amount);
        }
    }

    public void selectRate(String rate)
    {

//        Actions actions = new Actions(driver);
//        actions.moveToElement(intDropDown).perform();
//
//        WebElement chkBox = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/div/div/label[" + rate +"]/input"));
//        actions.moveToElement(chkBox).click().perform();
//
//
        if(isElementDisplayed(intDropDown,Duration.ofSeconds(2),true))
        {
            intDropDown.clear();
            intDropDown.click();
        }
        WebElement chkBox = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/div/div/label[" + rate +"]/input"));
        //actions.moveToElement(chkBox).click().perform();
        if(isElementDisplayed(chkBox,Duration.ofSeconds(2),true))
        {
            chkBox.clear();
            chkBox.click();
        }
    }

    public void selectDuration(String duration)
    {
       //Enter Duration for calaulating interest
        Select durationInput = new Select(durationSelect);
        if(isElementDisplayed(durationSelect,Duration.ofSeconds(2),true))
        {
            durationInput.deselectAll();
            durationInput.selectByVisibleText(duration);
        }

//        //Click on Calculate button
//        WebElement e = driver.findElement(By.xpath("//*[@id=\"interestForm\"]/button"));
//        actions.moveToElement(e).click().perform();
//        e.click();
//
    }

    public void clickCalculateButton()
    {
        if(isElementDisplayed(calculateButton,Duration.ofSeconds(2),true)) {
            calculateButton.click();
        }

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


}
