package pl.edu.pjwstk.MyRestController.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteCatByNameAndColorPage {
    private WebDriver webDriver;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "color")
    private WebElement colorInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public DeleteCatByNameAndColorPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public DeleteCatByNameAndColorPage open(){
        webDriver.get("http://localhost:8080/view/deleteByNameAndColorForm");
        return this;
    }

    public DeleteCatByNameAndColorPage fillInNameInput(String name){
        nameInput.sendKeys(name);
        return this;
    }

    public DeleteCatByNameAndColorPage fillInColorInput(String color){
        colorInput.sendKeys(color);
        return this;
    }

    public ViewAllPage submitForm(){
        submitButton.click();
        return new ViewAllPage(webDriver);
    }
}
