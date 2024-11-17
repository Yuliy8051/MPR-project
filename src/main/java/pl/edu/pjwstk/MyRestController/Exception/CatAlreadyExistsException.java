package pl.edu.pjwstk.MyRestController.Exception;

public class CatAlreadyExistsException extends RuntimeException{
    public CatAlreadyExistsException(){
        super("Cat already exists!");
    }
}
