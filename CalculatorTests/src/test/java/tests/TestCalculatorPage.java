package tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CalculaterPage;

import java.sql.SQLException;


public class TestCalculatorPage  {

//    public static ChromeOptions options;
//    public static WebDriver driver ;

    CalculaterPage calculatorPage =new CalculaterPage();

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

    @DataProvider
    public static Object[][] mandatoryFieldValidationTestData() {
        return new Object[][] {
                //amount, rate, duration,expectedInterest, expectedTotalamount
                {"100000","5","Yearly","Interest Amount: 5000.00","Total Amount with Interest: 105000.00"}
        };
    }

    @Test(dataProvider = "interestCalculationTestData")
    void test_calculate_interest (String amount,String rate, String duration, String expectedInterest, String expectedTotalAmount)  throws InterruptedException, SQLException {
        System.out.println("amount is:" + amount);
        System.out.println("interest rate is:" + rate);
        System.out.println("duration is:" + duration);
        System.out.println("expected calculated interest is:" + expectedInterest);
        System.out.println("expected total amount  is:" + expectedTotalAmount);

        calculatorPage.launchBrowser();

        //Enter Data
        calculatorPage.enterAmount(amount);
        calculatorPage.selectRate(rate);
        calculatorPage.selectDuration(duration);
        calculatorPage.clickCalculateButton();

        //Assert Results
        String calculatedInterest = calculatorPage.getCalculatedInterest();
        String totalAmount = calculatorPage.getTotalAmount();
        Assert.assertEquals(calculatedInterest, expectedInterest);
        Assert.assertEquals(totalAmount, expectedTotalAmount);
        close_browser();
    }

    @Test(dataProvider = "mandatoryFieldValidationTestData")
    void test_mandatory_field_validation(String amount,String rate, String duration, String expectedInterest, String expectedTotalAmount)  throws InterruptedException, SQLException {
        System.out.println("amount is:" + amount);
        System.out.println("interest rate is:" + rate);
        System.out.println("duration is:" + duration);
        System.out.println("expected calculated interest is:" + expectedInterest);
        System.out.println("expected total amount  is:" + expectedTotalAmount);

        calculatorPage.launchBrowser();

        //Enter Data
        calculatorPage.enterAmount(amount);
        calculatorPage.selectRate(rate);
        calculatorPage.selectDuration(duration);
        calculatorPage.clickCalculateButton();
        calculatorPage.clearAmount();
        String actualAlertText = calculatorPage.clickCalculateButtonForValidation();

        String expectedAlertText ="Please fill in all fields";
        //Assert alertbox validation message
        Assert.assertTrue(actualAlertText.contains(expectedAlertText),"Alert displayed for mandatory field - please fill in all fields");
        close_browser();
    }

    @AfterTest
    public void close_browser()
    {
        calculatorPage.closeBrowser();
    }


}
