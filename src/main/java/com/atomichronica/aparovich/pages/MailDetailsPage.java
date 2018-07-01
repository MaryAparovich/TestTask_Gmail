package com.atomichronica.aparovich.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MailDetailsPage extends AbstractPage {
    private final Logger logger = LogManager.getRootLogger();

    @FindBy(xpath = "//div[@role='listitem']//div[@dir='ltr']")
    WebElement textMessage;

    public MailDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public void openPage() {
        logger.info("Mail details page is opened");
    }

    public boolean isTextMessageEquals(String text) {
        if (textMessage.getText().equals(text)) {
            return true;
        }
        return false;
    }
}
