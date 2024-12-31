package pl.edu.pjwstk.MyRestController.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteCatTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp(){
        webDriver = new ChromeDriver();
    }

    @Test
    public void testDeleteCatForm(){
        Integer id = 1;
        boolean result = new DeleteCatPage(webDriver)
                .open()
                .fillInIdInput(id)
                .submitForm()
                .isVisible(id);
        assertFalse(result);
        webDriver.close();
    }
}
