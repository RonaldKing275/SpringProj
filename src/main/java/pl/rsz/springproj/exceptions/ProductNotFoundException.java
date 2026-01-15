package pl.rsz.springproj.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Nie znaleziono produktu")
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Nie znaleziono produktu o ID: " + id);
    }
}