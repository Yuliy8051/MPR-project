package pl.edu.pjwstk.MyRestController.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.MyRestController.exception.CatAlreadyExistsException;
import pl.edu.pjwstk.MyRestController.exception.CatHasInvalidFieldsException;
import pl.edu.pjwstk.MyRestController.exception.CatNotFoundException;
import pl.edu.pjwstk.MyRestController.repository.CatRepository;
import pl.edu.pjwstk.MyRestController.model.Cat;

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
//        repository.save(new Cat("Richard", "brown"));
//        repository.save(new Cat("Tihrik", "gray"));
//        repository.save(new Cat("Mishka", "white"));
    }

    public Iterable<Cat> getAll(){
        return repository.findAll();
    }

    public Iterable<Cat> getAllLower(){
        Iterable<Cat> cats = repository.findAll();
        cats.forEach(cat -> cat.setName(stringUnitsService.lower(cat.getName())));
        return cats;
    }

    public List<Cat> getByName(String name){
        List<Cat> cats = repository.findByName(name);
        if (cats.isEmpty())
            throw new CatNotFoundException();
        return cats;
    }

    public List<Cat> getByColor(String color) {
        List<Cat> cats = repository.findByColor(color);
        if (cats.isEmpty())
            throw new CatNotFoundException();
        return cats;
    }

    public Optional<Cat> getById(Long id){
        Optional<Cat> cat = repository.findById(id);
        if (cat.isEmpty())
            throw new CatNotFoundException();
        return cat;
    }

    public void addCat(Cat cat){
        if (doesSuchCatExist(cat))
            throw new CatAlreadyExistsException();
        throwCatHasInvalidFieldsExceptionIfNecessary(cat, false);
        repository.save(cat);
    }

    public void addUpperCat(Cat cat){
        throwCatHasInvalidFieldsExceptionIfNecessary(cat, false);
        cat.setName(stringUnitsService.upper(cat.getName()));
        if (doesSuchCatExist(cat))
            throw new CatAlreadyExistsException();
        repository.save(cat);
    }

    public void upgradeById(Long id, Cat cat){
        Optional<Cat> optionalCat = repository.findById(id);
        if (optionalCat.isEmpty()){
            throw new CatNotFoundException();
        }
        Cat existingCat = optionalCat.get();
        throwCatHasInvalidFieldsExceptionIfNecessary(cat, true);
        if (doesSuchCatExist(cat))
            throw new CatAlreadyExistsException();
        String name = cat.getName();
        String color = cat.getColor();
        if (name != null)
            existingCat.setName(name);
        if (color != null)
            existingCat.setColor(color);
        repository.save(existingCat);
    }

    public void deleteById(Long id){
        Optional<Cat> cat = repository.findById(id);
        if (cat.isEmpty())
            throw new CatNotFoundException();           // exception
        repository.deleteById(id);
    }


    private boolean doesSuchCatExist(Cat cat){
        List<Cat> cats = (List<Cat>) getAll();
        return !cats.stream().filter(c -> c.getIdentifier() == cat.getIdentifier()).toList().isEmpty();
    }

    private void throwCatHasInvalidFieldsExceptionIfNecessary(Cat cat, boolean canBeNull){
        String name = cat.getName();
        String color = cat.getColor();
        if (canBeNull){
            if (name != null && name.isEmpty() || color != null && color.isEmpty())
                throw new CatHasInvalidFieldsException();
        }
        else {
            if (name == null || name.isEmpty() || color == null || color.isEmpty())
                throw new CatHasInvalidFieldsException();
        }
    }
}
