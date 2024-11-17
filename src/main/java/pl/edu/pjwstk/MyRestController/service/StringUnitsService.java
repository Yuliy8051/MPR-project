package pl.edu.pjwstk.MyRestController.service;

import org.springframework.stereotype.Service;

@Service
public class StringUnitsService {
    public String upper(String string){
        return string.toUpperCase();
    }
    public String lower(String string){
        return string.charAt(0) + string.substring(1).toLowerCase();
    }
}
