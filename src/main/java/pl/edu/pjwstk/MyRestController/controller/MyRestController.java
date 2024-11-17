package pl.edu.pjwstk.MyRestController.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Iterable<Cat>> getAllCats(){
        return ResponseEntity.ok(catService.getAll());
    }

    @GetMapping("cat/id/{id}")
    public ResponseEntity<Optional<Cat>> getCat(@PathVariable("id") Long id){
        return ResponseEntity.ok(catService.getById(id));
    }

    @GetMapping("cat/name/{name}")
    public ResponseEntity<List<Cat>> findByName(@PathVariable String name){
        return ResponseEntity.ok(catService.getByName(name));
    }

    @GetMapping("cat/color/{color}")
    public ResponseEntity<List<Cat>> getByColor(@PathVariable String color){
        return ResponseEntity.ok(catService.getByColor(color));
    }

    @GetMapping("cat/allLower")
    public ResponseEntity<Iterable<Cat>> getAllLower(){
        return ResponseEntity.ok(catService.getAllLower());
    }

    @PostMapping("cat/add")
    public ResponseEntity<Void> addCat(@RequestBody Cat cat){
        catService.addCat(cat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("cat/addUpper")
    public ResponseEntity<Void> addUpperCat(@RequestBody Cat cat){
        catService.addUpperCat(cat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("cat/upgrade/id/{id}")
    public ResponseEntity<Void> upgradeById(@PathVariable Long id, @RequestBody Cat cat){
        catService.upgradeById(id, cat);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("cat/delete/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable("id") Long id){
        catService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
