package enrique.application.reunionManager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReventaoNotFoundException extends RuntimeException{
    public ReventaoNotFoundException(String message){
        super(message);
    }
}
