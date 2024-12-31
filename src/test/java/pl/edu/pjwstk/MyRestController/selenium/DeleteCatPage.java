package pl.edu.pjwstk.MyRestController.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteCatPage {
    private final WebDriver webDriver;

    @FindBy(id = "id")
    private WebElement idInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public DeleteCatPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public DeleteCatPage open(){
        webDriver.get("http://localhost:8080/view/deleteForm");
        return this;
    }

    public DeleteCatPage fillInIdInput(Integer id){
        idInput.sendKeys(id.toString());
        return this;
    }

    public ViewAllPage submitForm(){
        submitButton.click();
        return new ViewAllPage(webDriver);
    }
}
