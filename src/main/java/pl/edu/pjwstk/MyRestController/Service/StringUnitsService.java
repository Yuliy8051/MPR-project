package pl.edu.pjwstk.MyRestController.Service;

import org.springframework.stereotype.Service;

@Service
public class StringUnitsService {
    public String upper(String string){
        return string.toUpperCase();
    }
    public String lower(String string){
        return string.toLowerCase();
    }
}
