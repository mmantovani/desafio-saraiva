package br.com.saraiva.desafio.rest;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe para tratamento e retorno de respostas para situações de exceção.
 *
 * @author marco
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
