package pl.edu.pjwstk.MyRestController.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.MyRestController.Service.CatService;
import pl.edu.pjwstk.MyRestController.model.Cat;

import java.util.List;
import java.util.Optional;

@RestController
public class MyRestController {
    private CatService catService;

    @Autowired
    public MyRestController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("cat/all")
    public Iterable<Cat> getAllCats(){
        return catService.getAll();
    }

    @GetMapping("cat/id/{id}")
    public Optional<Cat> getCat(@PathVariable("id") Long id){
        return catService.getById(id);
    }

    @GetMapping("cat/name/{name}")
    public List<Cat> findByName(@PathVariable String name){
        return this.catService.getByName(name);
    }

    @GetMapping("cat/color/{color}")
    public List<Cat> getByColor(@PathVariable String color){
        return this.catService.getByColor(color);
    }

    @GetMapping("cat/allLower")
    public Iterable<Cat> getAllLower(){
        return catService.getAllLower();
    }

    @PostMapping("cat/add")
    public void addCat(@RequestBody Cat cat){
        this.catService.addCat(cat);
    }

    @PostMapping("cat/addUpper")
    public void addUpperCat(@RequestBody Cat cat){
        catService.addUpperCat(cat);
    }

    @PatchMapping("cat/upgrade/id/{id}")
    public void upgradeById(@PathVariable Long id, @RequestBody Cat cat){
        this.catService.upgradeById(id, cat);
    }

    @DeleteMapping("cat/delete/{id}")
    public void deleteCat(@PathVariable("id") Long id){
        catService.deleteById(id);
    }
}
