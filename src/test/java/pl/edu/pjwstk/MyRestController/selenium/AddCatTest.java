package pl.edu.pjwstk.MyRestController.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCatTest {
    private WebDriver webDriver;
    private String name;
    private String color;

    @BeforeEach
    public void setUp(){
        webDriver = new ChromeDriver();
        name = "test_data_name";
        color = "test_data_color";
    }

    @AfterEach
    public void clean(){
        new DeleteCatByNameAndColorPage(webDriver)
                .open()
                .fillInNameInput(name)
                .fillInColorInput(color)
                .submitForm()
                .close();
    }

    @Test
    public void testAddCatForm(){
        boolean result = new AddCatPage(webDriver)
                .open()
                .fillInNameInput(name)
                .fillInColorInput(color)
                .submitForm()
                .isVisible(name, color);
        assertTrue(result);
    }
}
