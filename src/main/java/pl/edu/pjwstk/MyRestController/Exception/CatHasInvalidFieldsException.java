package pl.edu.pjwstk.MyRestController.Exception;

public class CatHasInvalidFieldsException extends RuntimeException{
    public CatHasInvalidFieldsException(){
        super("Cat has invalid fields!");
    }
}
