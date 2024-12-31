package pl.edu.pjwstk.MyRestController.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pjwstk.MyRestController.model.Cat;
import pl.edu.pjwstk.MyRestController.service.CatService;

@Controller
public class CatViewController {
    private CatService catService;

    @Autowired
    public CatViewController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("view/all")
    public String displayAll(Model model){
        model.addAttribute("cats", catService.getAll());
        return "viewAll";
    }

    @GetMapping("view/addForm")
    public String displayAddForm(Model model){
        model.addAttribute("cat", new Cat());
        return "addForm";
    }

    @PostMapping("view/addForm")
    public String submitAddForm(@ModelAttribute Cat cat){
        catService.addCat(cat);
        return "redirect:/view/all";
    }

    @GetMapping("view/updateForm")
    public String displayUpdateForm(Model model){
        model.addAttribute("cat", new Cat());
        return "updateForm";
    }

    @PostMapping
    public String submitUpdateForm(@ModelAttribute Cat cat){
        catService.upgradeById(cat.getId(), cat);
        return "redirect:/view/all";
    }

    @GetMapping("view/deleteForm")
    public String displayDeleteForm(Model model){
        model.addAttribute("cat", new Cat());
        return "deleteForm";
    }

    @PostMapping("view/deleteForm")
    public String submitDeleteForm(@ModelAttribute Cat cat){
        catService.deleteById(cat.getId());
        return "redirect:/view/all";
    }

    @GetMapping("view/deleteByNameAndColorForm")
    public String displayDeleteByNameAndColorForm(Model model){
        model.addAttribute("cat", new Cat());
        return "deleteByNameAndColorForm";
    }

    @PostMapping("view/deleteByNameAndColorForm")
    public String submitDeleteByNameAndColorForm(@ModelAttribute Cat cat){
        catService.deleteByNameAndColor(cat.getName(), cat.getColor());
        return "redirect:/view/all";
    }
}
