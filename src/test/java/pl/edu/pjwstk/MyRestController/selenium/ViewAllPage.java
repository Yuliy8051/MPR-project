package pl.edu.pjwstk.MyRestController.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewAllPage {
    private WebDriver webDriver;

    public ViewAllPage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public ViewAllPage open(){
        webDriver.get("http://localhost:8080/view/all");
        return this;
    }

    public boolean isVisible(String name, String color){
        try {
            WebElement nameElement = webDriver.findElement(By.xpath("//*[text()='" + name + "']"));
            WebElement colorElement = webDriver.findElement(By.xpath("//*[text()='" + color + "']"));
            boolean isNameDisplayed = nameElement.isDisplayed();
            boolean isColorDisplayed = colorElement.isDisplayed();
            return isNameDisplayed && isColorDisplayed;
        }catch (NoSuchElementException ex){
            return false;
        }
    }

    public boolean isVisible(Integer id){
        try{
            WebElement nameElement = webDriver.findElement(By.id("row-" + id + "-name"));
            WebElement colorElement = webDriver.findElement(By.id("row-" + id + "-color"));
            boolean isNameDisplayed = nameElement.isDisplayed();
            boolean isColorDisplayed = colorElement.isDisplayed();
            return isNameDisplayed && isColorDisplayed;
        }catch (NoSuchElementException ex){
            return false;
        }
    }

    public void close(){
        webDriver.close();
    }
}
