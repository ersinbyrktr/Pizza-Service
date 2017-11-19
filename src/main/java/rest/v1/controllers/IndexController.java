package rest.v1.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.v1.pojos.Error;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity<Error> error(HttpServletRequest request) {
        Error newError = new Error(request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()));
        return new ResponseEntity<>(newError, HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}