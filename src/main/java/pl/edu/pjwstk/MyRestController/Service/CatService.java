package pl.edu.pjwstk.MyRestController.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.MyRestController.Exception.CatAlreadyExistsException;
import pl.edu.pjwstk.MyRestController.Exception.CatHasInvalidFieldsException;
import pl.edu.pjwstk.MyRestController.Exception.CatNotFoundException;
import pl.edu.pjwstk.MyRestController.Repository.CatRepository;
import pl.edu.pjwstk.MyRestController.model.Cat;

import java.lang.reflect.Field;
import java.text.FieldPosition;
import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private CatRepository repository;
    private StringUnitsService stringUnitsService;

    @Autowired
    public CatService(CatRepository repository, StringUnitsService stringUnitsService){
        this.stringUnitsService = stringUnitsService;
        this.repository = repository;
        repository.save(new Cat("Richard", "brown"));
        repository.save(new Cat("Tihrik", "gray"));
        repository.save(new Cat("Mishka", "white"));
    }

    public Iterable<Cat> getAll(){
        return repository.findAll();
    }

    public List<Cat> getByName(String name){
        return repository.findByName(name);
    }

    public List<Cat> getByColor(String color) {
        return this.repository.findByColor(color);
    }
    public Optional<Cat> getById(Long id){
        Optional<Cat> cat = repository.findById(id);
        if (cat.isEmpty())
            throw new CatNotFoundException();
        return repository.findById(id);
    }
    public Iterable<Cat> getAllLower(){
        Iterable<Cat> cats = repository.findAll();
        cats.forEach(cat -> cat.setName(stringUnitsService.lower(cat.getName())));
        cats.forEach(cat -> cat.setIdentifier());
        return cats;
    }

    public void addCat(Cat cat){
        List<Cat> cats = (List<Cat>) getAll();
        if (!cats.stream().filter(c -> c.getIdentifier() == cat.getIdentifier()).toList().isEmpty())
            throw new CatAlreadyExistsException();
        if (cat.getName().isEmpty() || cat.getColor().isEmpty())
            throw new CatHasInvalidFieldsException();
        repository.save(cat);
    }

    public void addUpperCat(Cat cat){
        List<Cat> cats = (List<Cat>) getAll();
        if (cats.stream().filter(c -> c.getIdentifier() == cat.getIdentifier()).toList().isEmpty())
            throw new CatAlreadyExistsException();
        cat.setName(stringUnitsService.upper(cat.getName()));
        if (cat.getName().isEmpty() || cat.getColor().isEmpty())
            throw new CatHasInvalidFieldsException();
        repository.save(cat);
    }

    public void upgradeById(Long id, Cat cat){
        Optional<Cat> optionalCat = repository.findById(id);
        if (optionalCat.isEmpty()){
            throw new CatNotFoundException();
        }
        List<Cat> cats = (List<Cat>) getAll();
        if (cats.stream().filter(c -> c.getIdentifier() == cat.getIdentifier()).toList().isEmpty())
            throw new CatAlreadyExistsException();
        Cat existingCat = optionalCat.get();
        String name = cat.getName();
        String color = cat.getColor();
        if (name != null)
            existingCat.setName(name);
        if (color != null)
            existingCat.setColor(color);
        existingCat.setIdentifier();
        if (cat.getName().isEmpty() || cat.getColor().isEmpty())
            throw new CatHasInvalidFieldsException();
        repository.save(cat);
    }

    public void deleteById(Long id){
        Optional<Cat> cat = repository.findById(id);
        if (cat.isEmpty())
            throw new CatNotFoundException();
        repository.deleteById(id);
    }
}
