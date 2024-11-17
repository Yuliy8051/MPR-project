package pl.edu.pjwstk.MyRestController.Exception;

public class CatNotFoundException extends RuntimeException{
    public CatNotFoundException(){
        super("Cat is not found!");
    }
}
