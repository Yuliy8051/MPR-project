package pl.edu.pjwstk.MyRestController.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UpdateCatPage {
    private WebDriver webDriver;

    @FindBy(id = "id")
    private WebElement idInput;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "color")
    private WebElement colorInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public UpdateCatPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public UpdateCatPage open(){
        this.webDriver.get("http://localhost:8080/view/updateForm");
        return this;
    }

    public UpdateCatPage fillInIdInput(String text){
        this.idInput.sendKeys(text);
        return this;
    }

    public UpdateCatPage fillInNameInput(String text){
        this.nameInput.sendKeys(text);
        return this;
    }

    public UpdateCatPage fillInColorInput(String text){
        this.colorInput.sendKeys(text);
        return this;
    }

    public ViewAllPage submitForm(){
        this.submitButton.click();
        return new ViewAllPage(webDriver);
    }
}
