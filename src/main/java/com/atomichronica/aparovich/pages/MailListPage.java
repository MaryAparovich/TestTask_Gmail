package com.atomichronica.aparovich.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MailListPage extends AbstractPage {

    private final Logger logger = LogManager.getRootLogger();
    private WebDriverWait driverWait = new WebDriverWait(driver, 200);
    private MailDetailsPage mailDetailsPage;
    @FindBy(xpath = "//a[@href='https://mail.google.com/mail/u/0/#inbox']")
    WebElement inboxLink;

    @FindBy(xpath = "//div[@role='tabpanel']//tr")
    List<WebElement> inboxEmailsItem;

    @FindBy(xpath = "//div[@gh='cm']")
    WebElement composeButton;

    @FindBy(name = "to")
    WebElement recipientField;

    @FindBy(name = "subjectbox")
    WebElement subjectField;

    @FindBy(xpath = "//div[@role='textbox']")
    WebElement messageBody;

    @FindBy(xpath = "//table[@role='group']//div[@role='button'][1]")
    WebElement sendButton;

    public MailListPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public void openPage() {
        logger.info("Mail list page is opened");
    }

    public void composeAndSendLetter(String recipientEmail, String subject, String textMessage) {
        composeButton.click();
        driverWait.until(ExpectedConditions.visibilityOf(recipientField));
        recipientField.sendKeys(recipientEmail);
        subjectField.sendKeys(subject);
        messageBody.sendKeys(textMessage);
        sendButton.click();
        logger.info("Message sent");
    }

    public void clickOnInboxMessages() {
        inboxLink.click();
        logger.info("Clicked on the inbox messages");
    }

    public boolean isDeliveredLetter (String subject, String recipientEmail, String textMessage)  {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading")));

        List<WebElement> listElements = inboxEmailsItem;

        for (int i = 0; i < listElements.size(); i++) {

            try {
                listElements.get(i).findElement(By.xpath(".//span[@email='" + recipientEmail + "']"));
                String subjectText = listElements.get(i).findElement(By.xpath(".//span[@class='bog']/b")).getText();

                if (subjectText.equals(subject)) {
                    listElements.get(i).click();
                    mailDetailsPage = new MailDetailsPage(driver);
                    mailDetailsPage.openPage();

                    if (mailDetailsPage.isTextMessageEquals(textMessage)) {
                        return true;
                    } else {
                        driver.navigate().back();
                    }
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
