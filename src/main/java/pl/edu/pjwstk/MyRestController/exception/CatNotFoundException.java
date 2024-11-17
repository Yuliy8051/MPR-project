package pl.edu.pjwstk.MyRestController.exception;

public class CatNotFoundException extends RuntimeException{
    public CatNotFoundException(){
        super("Cat is not found!");
    }
}
