package pl.edu.pjwstk.MyRestController.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.MyRestController.exception.CatAlreadyExistsException;
import pl.edu.pjwstk.MyRestController.exception.CatHasInvalidFieldsException;
import pl.edu.pjwstk.MyRestController.exception.CatNotFoundException;
import pl.edu.pjwstk.MyRestController.repository.CatRepository;
import pl.edu.pjwstk.MyRestController.model.Cat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private final CatRepository repository;
    private final StringUnitsService stringUnitsService;

    @Autowired
    public CatService(CatRepository repository, StringUnitsService stringUnitsService){
        this.stringUnitsService = stringUnitsService;
        this.repository = repository;
        repository.save(new Cat("Tihrik", "Gray"));
        repository.save(new Cat("Richard", "Brown"));
        repository.save(new Cat("Michka", "White"));
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

    public void getPdfById(Long id, HttpServletResponse response){
        Optional<Cat> optionalCat = repository.findById(id);
        if (optionalCat.isEmpty())
            throw new CatNotFoundException();
        Cat cat = optionalCat.get();
        PDDocument pdf = new PDDocument();
        generatePdf(cat, pdf, response);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;");
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
            throw new CatNotFoundException();
        repository.deleteById(id);
    }

    public void deleteByNameAndColor(String name, String color){
        List<Cat> cat = repository.findByNameAndColor(name, color);
        if (cat.isEmpty())
            throw new CatNotFoundException();
        repository.deleteByNameAndColor(name, color);
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

    private void generatePdf(Cat cat, PDDocument pdf, HttpServletResponse response){
        String idInformation = "Id: " + cat.getId();
        String nameInformation = "Name: " + cat.getName();
        String colorInformation = "Color: " + cat.getColor();
        String identifierInformation = "Identifier: " + cat.getIdentifier();
        PDPage page = new PDPage();
        pdf.addPage(page);
        try {
            PDPageContentStream stream = new PDPageContentStream(pdf, page);
            stream.beginText();
            stream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 14);
            stream.setLeading(14f);
            stream.newLineAtOffset(20, 750);
            stream.showText(idInformation);
            stream.newLine();
            stream.showText(nameInformation);
            stream.newLine();
            stream.showText(colorInformation);
            stream.newLine();
            stream.showText(identifierInformation);
            stream.endText();
            stream.close();
            pdf.save(response.getOutputStream());
            pdf.close();
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
