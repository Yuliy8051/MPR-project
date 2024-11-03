import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pjwstk.MyRestController.Service.StringUnitsService;

public class StringUnitsServiceTest {
    private StringUnitsService testedService;
    @BeforeEach
    public void setUp(){
        testedService = new StringUnitsService();
    }
    @Test
    public void upperReturnsStringWithUpperCase(){
        String s = "aBc";
        String result = testedService.upper(s);
        Assertions.assertEquals("ABC", result);
    }
    @Test
    public void lowerReturnsStringWithLowerCase(){
        String s = "aBc";
        String result = testedService.lower(s);
        Assertions.assertEquals("abc", result);
    }
}
