package cc.ytleonlin.currencyapi.web.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {

    public BadRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
