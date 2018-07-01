package com.atomichronica.aparovich;

import com.atomichronica.aparovich.driver.DriverSingleton;
import com.atomichronica.aparovich.pages.LoginPage;
import com.atomichronica.aparovich.pages.MailListPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class GmailTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private MailListPage mailListPage;
    private static final String EMAIL = "test.automation45651@gmail.com";
    private static final String PASSWORD = "12345qwertyui";
    private static final String RECIPIENT_EMAIL = "test.automation45651@gmail.com";
    private static final String SUBJECT = "Test";
    private static final String TEXT_MESSAGE = "Hello, world!";

    @BeforeMethod(groups = {"gmail"}, description = "Init browser")
    public void setUp() {
        driver = DriverSingleton.getDriver();
        loginPage = new LoginPage(driver);
        mailListPage = new MailListPage(driver);
    }

    @Test(groups = {"gmail"})
    public void checkDeliveryLetter() {
        loginPage.openPage();
        loginPage.login(EMAIL, PASSWORD);

        mailListPage.openPage();
        mailListPage.composeAndSendLetter(RECIPIENT_EMAIL, SUBJECT, TEXT_MESSAGE);
        mailListPage.clickOnInboxMessages();
        assertTrue(mailListPage.isDeliveredLetter(SUBJECT, RECIPIENT_EMAIL, TEXT_MESSAGE));
    }

    @AfterMethod(groups = {"gmail"}, description = "Stop Browser")
    public void stopBrowser() {
        DriverSingleton.closeDriver();
    }
}
