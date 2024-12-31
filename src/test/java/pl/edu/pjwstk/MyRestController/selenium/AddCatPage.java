package pl.edu.pjwstk.MyRestController.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCatPage {
    private WebDriver webDriver;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "color")
    private WebElement colorInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public AddCatPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public AddCatPage open(){
        webDriver.get("http://localhost:8080/view/addForm");
        return this;
    }

    public AddCatPage fillInNameInput(String text){
        nameInput.sendKeys(text);
        return this;
    }

    public AddCatPage fillInColorInput(String text){
        colorInput.sendKeys(text);
        return this;
    }

    public ViewAllPage submitForm(){
        submitButton.click();
        return new ViewAllPage(webDriver);
    }

//    public boolean isHeaderVisible(){
//        return submitButton.isDisplayed();
//    }
}
