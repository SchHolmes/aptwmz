import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class SeleniumTest {

    private WebDriver browser = new ChromeDriver();

    private static String QUALIFICATIONS_LACK = "Brak kwalifikacji";
    private static String JUNIOR = "Junior";
    private static String GNOME = "Skrzat";
    private static String YOUNGSTER = "Mlodzik";
    private static String ADULT = "Dorosly";
    private static String SENIOR = "Senior";
    private static String ERROR = "Blad danych";

    @Before
    public void runBrowser() {
        browser.get("https://lamp.ii.us.edu.pl/~mtdyd/zawody/");
    }

    @After
    public void closeBrowser() {
        browser.close();
    }

    @Test
    public void test01() {
        runTestWithData(true, true,9, false, false, QUALIFICATIONS_LACK);
    }

    @Test
    public void test02() {
        runTestWithData(true, true,10, true, true, GNOME);
    }

    @Test
    public void test03() {
        runTestWithData(true, true,11, true, true, GNOME);
    }

    @Test
    public void test04() {
        runTestWithData(true, true,12, true, true, YOUNGSTER);
    }

    @Test
    public void test05() {
        runTestWithData(true, true,13, true, true, YOUNGSTER);
    }

    @Test
    public void test06() {
        runTestWithData(true, true,14, true, true, JUNIOR);
    }

    @Test
    public void test07() {
        runTestWithData(true, true,17, true, true, JUNIOR);
    }

    @Test
    public void test08() {
        runTestWithData(true, true,18, false, false, ADULT);
    }

    @Test
    public void test09() {
        runTestWithData(true, true,64, false, false, ADULT);
    }

    @Test
    public void test10() {
        runTestWithData(true, true,65, false, true, SENIOR);
    }

    @Test
    public void test11() {
        runTestWithData(true, true,15, true, true, JUNIOR);
    }

    @Test
    public void test12() {
        runTestWithData(true, true,15, false, true, ERROR);
    }

    @Test
    public void test13() {
        runTestWithData(true, true,15, false, false, ERROR);
    }

    @Test
    public void test14() {
        runTestWithData(true, true,70, false, true, SENIOR);
    }

    @Test
    public void test15() {
        runTestWithData(true, true,70, false, false, ERROR);
    }

    @Test
    public void test16() {
        runTestWithData(true, false,10, true, true, ERROR);
    }

    @Test
    public void test17() {
        runTestWithData(false, true,10, true, true, ERROR);
    }

    @Test
    public void test18() {
        runTestWithData(false, false,10, true, true, ERROR);
    }

    private void runTestWithData(boolean isName,
                                 boolean isSurname,
                                 int age,
                                 boolean isParentalConsent,
                                 boolean isDoctorsPermission,
                                 String expected) {

        if(isName)
            getNameElement().sendKeys(getPseudoRandomName());

        if(isSurname)
            getSurnameElement().sendKeys(getPseudoRandomSurname());

        getBirthDateElement().sendKeys(getDateString(age));

        if(isParentalConsent)
            getParentalConsentElement().click();

        if(isDoctorsPermission)
            getDoctorsPermissionElement().click();

        clickSubmit();

        assertEquals(expected, getAlertMessage());
    }

    private WebElement getNameElement() {
        return browser.findElement(By.id("inputEmail3"));
    }

    private WebElement getSurnameElement() {
        return browser.findElement(By.id("inputPassword3"));
    }

    private WebElement getBirthDateElement() {
        return browser.findElement(By.id("dataU"));
    }

    private WebElement getParentalConsentElement() {
        return browser.findElement(By.id("rodzice"));
    }

    private WebElement getDoctorsPermissionElement() {
        return browser.findElement(By.id("lekarz"));
    }

    private String getAlertMessage() {
        try {
            browser.switchTo().alert().dismiss();

            String message = browser.switchTo().alert().getText();

            browser.switchTo().alert().dismiss();

            return message;
        } catch (NoAlertPresentException e) {
            return ERROR;
        }
    }

    private void clickSubmit() {
        browser.findElement(By.cssSelector("button")).click();
    }

    private String getDateString(int age) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy ");
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.minusYears(age + 1);

        return dateTimeFormatter.format(localDateTime);
    }

    private String getPseudoRandomName() {
        return "Jan";
    }
    
    private String getPseudoRandomSurname() {
        return "Kowalski";
    }
}
