import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.pjwstk.MyRestController.Repository.CatRepository;
import pl.edu.pjwstk.MyRestController.Service.CatService;
import pl.edu.pjwstk.MyRestController.Service.StringUnitsService;
import pl.edu.pjwstk.MyRestController.model.Cat;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CatServiceTest {
    private CatRepository catRepository;
    private StringUnitsService stringUnitsService;
    private CatService testedService;

    @BeforeEach
    public void setUp(){
        catRepository = Mockito.mock(CatRepository.class);
        stringUnitsService = Mockito.mock(StringUnitsService.class);
        testedService = new CatService(catRepository, stringUnitsService);
    }

    @Test
    public void getAllReturnsAllCats(){
        List<Cat> cats = List.of(mock(Cat.class), mock(Cat.class));
        when(catRepository.findAll()).thenReturn(cats);
        Iterable<Cat> result = testedService.getAll();
        verify(catRepository).findAll();
        Assertions.assertIterableEquals(cats, result);
    }
    @Test
    public void getByNameReturnsListOfCatsWithGivenName(){
        String name = "Sasha";
        List<Cat> catsWithCertainName = List.of(mock(Cat.class), mock(Cat.class));
        when(catRepository.findByName(any())).thenReturn(catsWithCertainName);
        List<Cat> result = testedService.getByName(name);
        verify(catRepository).findByName(name);
        Assertions.assertIterableEquals(catsWithCertainName, result);
    }
    @Test
    public void getByColorReturnsListOfCatsWithGivenColor(){
        String color = "Gray";
        List<Cat> catsWithCertainColor = List.of(mock(Cat.class),mock(Cat.class));
        when(catRepository.findByColor(any())).thenReturn(catsWithCertainColor);
        List<Cat> result = testedService.getByColor(color);
        verify(catRepository).findByColor(any());
        Assertions.assertIterableEquals(catsWithCertainColor, result);
    }
    @Test
    public void getByIdReturnsCatWithGivenId(){
        Long id = 2L;
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));
        when(catRepository.findById(any())).thenReturn(optionalCat);
        Optional<Cat> result = testedService.getById(id);
        verify(catRepository).findById(any());
        Assertions.assertEquals(optionalCat, result);
    }
    @Test
    public void getAllLowerReturnsAllCatsWithLowerCase(){
        List<Cat> cats = List.of(mock(Cat.class), mock(Cat.class));

        when(catRepository.findAll()).thenReturn(cats);

        Iterable<Cat> result = testedService.getAllLower();
        verify(catRepository).findAll();
        verify(stringUnitsService, times(cats.size())).lower(any());
        for (Cat cat : cats){
            verify(cat).setName(any());
            verify(cat).setIdentifier();
        }
        Assertions.assertIterableEquals(cats, result);
    }
    @Test
    public void addCatAddsCat(){
        Cat cat = mock(Cat.class);
        testedService.addCat(cat);
        verify(catRepository, times(4)).save(any());
    }
    @Test
    public void addUpperCatAddsCatWithUpperCaseName(){
        Cat cat = mock(Cat.class);
        testedService.addUpperCat(cat);
        verify(stringUnitsService).upper(any());
        verify(catRepository, times(4)).save(any());
    }
    @Test
    public void upgradeByIdUpgradesCatWhenCatWithGivenIdExistsNameAndColorAreGiven(){
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
        verify(cat).getName();
        verify(cat).getColor();
        verify(optionalCat.get()).setColor(any());
        verify(optionalCat.get()).setName(any());
        verify(catRepository, times(4)).save(any());
    }
    @Test
    public void upgradeByIdUpgradesCatWhenCatWithGivenIdExistsNameIsGivenAndColorIsNot(){
        Long id = 2L;
        Cat cat = mock(Cat.class);
        String name = "Sasha";
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));

        when(catRepository.findById(any())).thenReturn(optionalCat);
        when(cat.getName()).thenReturn(name);

        testedService.upgradeById(id, cat);
        verify(catRepository).findById(any());
        verify(cat).getName();
        verify(cat).getColor();
        verify(optionalCat.get(), times(0)).setColor(any());
        verify(optionalCat.get()).setName(any());
        verify(catRepository, times(4)).save(any());
    }
    @Test
    public void upgradeByIdUpgradesCatWhenCatWithGivenIdExistsNameIsNotGivenAndColorIs(){
        Long id = 2L;
        Cat cat = mock(Cat.class);
        String color = "Gray";
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));

        when(catRepository.findById(any())).thenReturn(optionalCat);
        when(cat.getColor()).thenReturn(color);

        testedService.upgradeById(id, cat);
        verify(catRepository).findById(any());
        verify(cat).getName();
        verify(cat).getColor();
        verify(optionalCat.get()).setColor(any());
        verify(optionalCat.get(), times(0)).setName(any());
        verify(catRepository, times(4)).save(any());
    }
    @Test
    public void upgradeByIdUpgradesCatWhenCatWithGivenIdExistsNameAndColorAreNotGiven(){
        Long id = 2L;
        Cat cat = mock(Cat.class);
        Optional<Cat> optionalCat = Optional.of(mock(Cat.class));

        when(catRepository.findById(any())).thenReturn(optionalCat);

        testedService.upgradeById(id, cat);
        verify(catRepository).findById(any());
        verify(cat).getName();
        verify(cat).getColor();
        verify(optionalCat.get(), times(0)).setColor(any());
        verify(optionalCat.get(), times(0)).setName(any());
        verify(catRepository, times(4)).save(any());
    }
    @Test
    public void upgradeByIdDoesNotUpgradeCatWhenCatWithGivenIdDoesNotExist(){
        Long id = 2L;
        Cat cat = mock(Cat.class);
        Optional<Cat> optionalCat = Optional.empty();

        when(catRepository.findById(any())).thenReturn(optionalCat);

        testedService.upgradeById(id, cat);
        verify(catRepository).findById(any());
        verify(cat, times(0)).getName();
        verify(cat, times(0)).getColor();
        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> optionalCat.get());
        verify(catRepository, times(3)).save(any());
    }
    @Test
    public void deleteByIdDeletesCatFromRepository(){
        Long id = 2L;
        testedService.deleteById(id);
        verify(catRepository).deleteById(any());
    }
}