package pl.edu.pjwstk.MyRestController;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import pl.edu.pjwstk.MyRestController.exception.CatAlreadyExistsException;
import pl.edu.pjwstk.MyRestController.exception.CatHasInvalidFieldsException;
import pl.edu.pjwstk.MyRestController.exception.CatNotFoundException;
import pl.edu.pjwstk.MyRestController.repository.CatRepository;
import pl.edu.pjwstk.MyRestController.service.CatService;
import pl.edu.pjwstk.MyRestController.service.StringUnitsService;
import pl.edu.pjwstk.MyRestController.model.Cat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CatServiceTest {
    @Mock
    private CatRepository catRepository;
    @Mock
    private StringUnitsService stringUnitsService;
    @InjectMocks
    private CatService testedService;

    public CatServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAllCats(){
        List<Cat> cats = List.of(mock(Cat.class), mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        Iterable<Cat> result = testedService.getAll();
        verify(catRepository).findAll();
        assertIterableEquals(cats, result);
    }
    @Test
    public void shouldReturnAllCatsWithNamesToLowerCase(){
        List<Cat> cats = List.of(mock(Cat.class), mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        Iterable<Cat> result = testedService.getAllLower();
        verify(catRepository).findAll();
        verify(stringUnitsService, times(cats.size())).lower(any());
        for (Cat cat : cats)
            verify(cat).setName(any());
        assertIterableEquals(cats, result);
    }
    @Test
    public void shouldReturnCatsWithGivenNameWhenTheyExist(){
        List<Cat> catsWithCertainName = List.of(mock(Cat.class), mock(Cat.class));
        when(catRepository.findByName(any())).thenReturn(catsWithCertainName);
        List<Cat> result = testedService.getByName(any());
        verify(catRepository).findByName(any());
        assertIterableEquals(catsWithCertainName, result);
    }
    @Test
    public void shouldNotReturnCatsWithGivenNameWhenTheyDoNotExist(){
        List<Cat> emptyList = List.of();
        when(catRepository.findByName(any())).thenReturn(emptyList);
        assertThrowsExactly(CatNotFoundException.class, () -> testedService.getByName(any()));
    }
    @Test
    public void shouldReturnCatsWithGivenColorWhenTheyExist(){
        List<Cat> catsWithCertainColor = List.of(mock(Cat.class),mock(Cat.class));
        when(catRepository.findByColor(any())).thenReturn(catsWithCertainColor);
        List<Cat> result = testedService.getByColor(any());
        verify(catRepository).findByColor(any());
        assertIterableEquals(catsWithCertainColor, result);
    }
    @Test
    public void shouldNotReturnCatsWithGivenColorWhenTheyDoNotExist(){
        when(catRepository.findByColor(any())).thenReturn(List.of());
        assertThrowsExactly(CatNotFoundException.class, () -> testedService.getByColor(any()));
    }
    @Test
    public void shouldReturnCatWithGivenIdWhenItExists(){
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));
        when(catRepository.findById(any())).thenReturn(optionalCat);
        Optional<Cat> result = testedService.getById(any());
        verify(catRepository).findById(any());
        Assertions.assertEquals(optionalCat, result);
    }
    @Test
    public void shouldNotReturnCatWithGivenIdWhenItDoesNotExist(){
        when(catRepository.findById(any())).thenReturn(Optional.empty());
        assertThrowsExactly(CatNotFoundException.class, () -> testedService.getById(any()));
    }
    @Test
    public void shouldAddCat(){
        Cat cat = new Cat("Sasha", "Gray");
        Iterable<Cat> cats = List.of(Mockito.mock(Cat.class), Mockito.mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        testedService.addCat(cat);
        verify(catRepository).save(any());
    }
    @Test
    public void shouldNotAddCatWhenCatWithSuchIdentifierExists(){
        Cat cat = new Cat("Sasha", "Gray");
        Iterable<Cat> cats = List.of(new Cat("Sashb", "Grax"));
        when(catRepository.findAll()).thenReturn(cats);
        assertThrowsExactly(CatAlreadyExistsException.class, () -> testedService.addCat(cat));
    }
    @Test
    public void shouldNotAddCatWhenCatNameIsEmpty(){
        Cat cat = new Cat("", "Gray");
        Iterable<Cat> cats = List.of(Mockito.mock(Cat.class), Mockito.mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.addCat(cat));
    }
    @Test
    public void shouldNotAddCatWhenCatNameIsNull(){
        Cat cat = new Cat(null, "Gray");
        Iterable<Cat> cats = List.of(Mockito.mock(Cat.class), Mockito.mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.addCat(cat));
    }
    @Test
    public void shouldNotAddCatWhenCatColorIsEmpty(){
        Cat cat = new Cat("Sasha", "");
        Iterable<Cat> cats = List.of(Mockito.mock(Cat.class), Mockito.mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.addCat(cat));
    }
    @Test
    public void shouldNotAddCatWhenCatColorIsNull(){
        Cat cat = new Cat("Sasha", "");
        Iterable<Cat> cats = List.of(Mockito.mock(Cat.class), Mockito.mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.addCat(cat));
    }
    @Test
    public void shouldAddCatWithUpperCaseName(){
        Cat cat = new Cat("Sasha", "Gray");
        Iterable<Cat> cats = List.of(Mockito.mock(Cat.class), Mockito.mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        when(stringUnitsService.upper("Sasha")).thenReturn("SASHA");
        testedService.addUpperCat(cat);
        verify(stringUnitsService).upper(any());
        verify(catRepository).save(any());
    }
    @Test
    public void shouldNotAddCatWithUpperCaseNameWhenCatWithSuchIdentifierExists(){
        Cat cat = new Cat("Sasha", "Gray");
        Iterable<Cat> cats = List.of(new Cat("SASHB", "Grax"));
        when(catRepository.findAll()).thenReturn(cats);
        when(stringUnitsService.upper(cat.getName())).thenReturn("SASHA");
        assertThrowsExactly(CatAlreadyExistsException.class, () -> testedService.addUpperCat(cat));
    }
    @Test
    public void shouldNotAddCatWithUpperCaseNameWhenCatNameIsEmpty(){
        Cat cat = new Cat("", "Gray");
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.addUpperCat(cat));
    }
    @Test
    public void shouldNotAddCatWithUpperCaseNameWhenCatNameIsNull(){
        Cat cat = new Cat(null, "Gray");
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.addUpperCat(cat));
    }
    @Test
    public void shouldNotAddCatWithUpperCaseNameWhenCatColorIsEmpty(){
        Cat cat = new Cat("Sasha", "");
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.addUpperCat(cat));
    }
    @Test
    public void shouldNotAddCatWithUpperCaseNameWhenCatColorIsNull(){
        Cat cat = new Cat("Sasha", null);
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.addUpperCat(cat));
    }
    @Test
    public void shouldUpgradeCat(){
        Long id = 2L;
        Cat cat = mock(Cat.class);
        String name = "Sasha";
        String color = "Gray";
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));

        when(catRepository.findById(any())).thenReturn(optionalCat);
        when(cat.getName()).thenReturn(name);
        when(cat.getColor()).thenReturn(color);

        testedService.upgradeById(id, cat);
        verify(catRepository).findById(any());
        verify(cat, times(2)).getName();
        verify(cat, times(2)).getColor();
        verify(optionalCat.get()).setColor(any());
        verify(optionalCat.get()).setName(any());
        verify(catRepository).save(any());
    }
    @Test
    public void shouldUpgradeCatWhenGivenNameIsNull(){
        Long id = 2L;
        String name = null;
        String color = "Gray";
        Cat cat = new Cat(name, color);
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));

        when(catRepository.findById(any())).thenReturn(optionalCat);

        testedService.upgradeById(id, cat);
        verify(catRepository).findById(any());
        verify(optionalCat.get()).setColor(any());
        verify(optionalCat.get(), times(0)).setName(any());
        verify(catRepository).save(any());
    }
    @Test
    public void shouldUpgradeCatWhenGivenColorIsNull(){
        Long id = 2L;
        String name = "Sasha";
        String color = null;
        Cat cat = new Cat(name, color);
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));

        when(catRepository.findById(any())).thenReturn(optionalCat);

        testedService.upgradeById(id, cat);
        verify(catRepository).findById(any());
        verify(optionalCat.get()).setName(any());
        verify(optionalCat.get(), times(0)).setColor(any());
        verify(catRepository).save(any());
    }
    @Test
    public void shouldNotUpgradeCatWhenItDoesNotExist(){
        Long id = 2L;
        Cat cat = mock(Cat.class);
        when(catRepository.findById(any())).thenReturn(Optional.empty());
        assertThrowsExactly(CatNotFoundException.class, () -> testedService.upgradeById(id, cat));
    }
    @Test
    public void shouldNotUpgradeCatWhenGivenNameIsEmpty(){
        Long id = 2L;
        String name = "";
        String color = "Gray";
        Cat cat = new Cat(name, color);
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));
        when(catRepository.findById(any())).thenReturn(optionalCat);
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.upgradeById(id, cat));

    }
    @Test
    public void shouldNotUpgradeCatWhenGivenColorIsEmpty(){
        Long id = 2L;
        String name = "Sasha";
        String color = "";
        Cat cat = new Cat(name, color);
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));
        when(catRepository.findById(any())).thenReturn(optionalCat);
        assertThrowsExactly(CatHasInvalidFieldsException.class, () -> testedService.upgradeById(id, cat));
    }
    @Test
    public void shouldNotUpgradeCatWhenCatWithGivenIdentifierExists(){
        Long id = 2L;
        String name = "Sasha";
        String color = "Gray";
        Cat cat = new Cat(name, color);
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));
        when(catRepository.findById(any())).thenReturn(optionalCat);
        when(catRepository.findAll()).thenReturn(List.of(new Cat("Sashb", "Grax")));
        assertThrowsExactly(CatAlreadyExistsException.class, () -> testedService.upgradeById(id, cat));
    }
    @Test
    public void shouldDeleteCatWithGivenId(){
        Long id = 2L;
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));
        when(catRepository.findById(any())).thenReturn(optionalCat);
        testedService.deleteById(id);
        verify(catRepository).findById(any());
        verify(catRepository).deleteById(any());
    }
    @Test
    public void shouldNotDeleteCatWithGivenIdWhenCatDoesNotExist(){
        Long id = 2L;
        Optional<Cat> optionalCat = Optional.empty();
        when(catRepository.findById(any())).thenReturn(optionalCat);
        assertThrowsExactly(CatNotFoundException.class, () -> testedService.deleteById(id));
    }
    @Test
    public void shouldReturnPdfWithFullInformation() throws IOException {
        Cat cat = mock(Cat.class);
        Optional<Cat> optionalCat = spy(Optional.of(cat));
        HttpServletResponse response = spy(MockHttpServletResponse.class);

        when(catRepository.findById(anyLong())).thenReturn(optionalCat);
        when(cat.getId()).thenReturn(4L);
        when(cat.getName()).thenReturn("Sasha");
        when(cat.getColor()).thenReturn("Gray");
        when(cat.getIdentifier()).thenReturn(456);

        testedService.getPdfById(anyLong(), response);
        verify(optionalCat).isEmpty();
        verify(optionalCat).get();
        verify(cat).getId();
        verify(cat).getName();
        verify(cat).getColor();
        verify(cat).getIdentifier();
        verify(response).getOutputStream();

    }
    @Test
    public void shouldNotReturnPdf(){
        Optional<Cat> optionalCat = Optional.empty();
        when(catRepository.findById(anyLong())).thenReturn(optionalCat);
        assertThrowsExactly(CatNotFoundException.class,
                () -> testedService.getPdfById(anyLong(), new Response()));
    }
}