package pl.edu.pjwstk.MyRestController.model;

import jakarta.persistence.*;

@Entity
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String color;
    private int identifier;

    public Cat() {
        setIdentifier();
    }

    public Cat(String name, String color) {
        this.name = name;
        this.color = color;
        setIdentifier();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setIdentifier();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        setIdentifier();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(){
        this.identifier = 0;
        String nameAndColor = this.name + this.color;
        for (int i = 0; i < nameAndColor.length(); i++) {
            this.identifier += nameAndColor.charAt(i);
        }
    }
}
