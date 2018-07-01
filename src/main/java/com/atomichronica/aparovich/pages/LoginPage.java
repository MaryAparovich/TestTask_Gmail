package com.atomichronica.aparovich.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractPage {
    private final Logger logger = LogManager.getRootLogger();
    private WebDriverWait driverWait = new WebDriverWait(driver, 200);
    private final String BASE_URL = "https://www.gmail.com";

    @FindBy(name = "identifier")
    private WebElement inputName;

    @FindBy(name = "password")
    private WebElement inputPassword;

    @FindBy(id = "identifierNext")
    private WebElement loginButtonNext;

    @FindBy(id = "passwordNext")
    private WebElement passwordButtonNext;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    @Override
    public void openPage() {
        driver.navigate().to(BASE_URL);
        logger.info("Authorization page is opened");
    }

    public void login(String email, String password) {
        inputName.sendKeys(email);
        loginButtonNext.click();
        driverWait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys(password);
        passwordButtonNext.click();
    }
}
