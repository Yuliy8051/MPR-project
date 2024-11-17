package pl.edu.pjwstk.MyRestController.exception;

public class CatAlreadyExistsException extends RuntimeException{
    public CatAlreadyExistsException(){
        super("Cat already exists!");
    }
}
