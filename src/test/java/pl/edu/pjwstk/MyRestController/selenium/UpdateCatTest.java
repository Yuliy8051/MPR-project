package pl.edu.pjwstk.MyRestController.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateCatTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp(){
        this.webDriver = new ChromeDriver();
    }

    @Test
    public void testUpdateCatForm(){
        UpdateCatPage updateCatPage = new UpdateCatPage(webDriver);
        String name = "Sasha";
        String color = "Gray";
        boolean result = updateCatPage
                .open()
                .fillInIdInput("1")
                .fillInNameInput(name)
                .fillInColorInput(color)
                .submitForm()
                .isVisible(name, color);
        webDriver.close();
        assertTrue(result);
    }
}
