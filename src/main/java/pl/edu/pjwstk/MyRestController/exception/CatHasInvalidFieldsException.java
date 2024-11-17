package pl.edu.pjwstk.MyRestController.exception;

public class CatHasInvalidFieldsException extends RuntimeException{
    public CatHasInvalidFieldsException(){
        super("Cat has invalid fields!");
    }
}
