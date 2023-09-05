package by.andd3dfx.templateapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class NotFoundException extends RuntimeException {

    public NotFoundException(String name, String id) {
        super(String.format("Could not find %s by id=%s", name, id));
    }
}
