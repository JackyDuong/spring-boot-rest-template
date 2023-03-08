package ch.duong.jmd.#APP_ABBREVIATION.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.FileNotFoundException;

@RestControllerAdvice
public class GlobalExceptionControllerHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionControllerHandler.class);

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError noBodyException(HttpMessageNotReadableException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError illegalStateException(IllegalStateException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError badRequest(NullPointerException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError illegalArgumentException(IllegalArgumentException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EmptyDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError emptyDataException(EmptyDataException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.EMPTY_DATA);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpError entityNotFoundException(EntityNotFoundException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.ENTITY_NOT_FOUND);
    }

    @ExceptionHandler(value = {FileNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpError fileNotFoundException(FileNotFoundException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.NOT_FOUND);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError dataIntegrityViolationException(DataIntegrityViolationException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public HttpError httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {InsufficientStorageException.class})
    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    public HttpError insufficientStorageException(InsufficientStorageException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.INSUFFICIENT_STORAGE);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HttpError accessDeniedException(AccessDeniedException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.ACCESS_DENIED);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HttpError authenticationException(AuthenticationException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {EmptyTokenException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HttpError emptyTokenException(EmptyTokenException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HttpError expiredJwtException(ExpiredJwtException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.EXPIRED_TOKEN);
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HttpError malformedJwtException(MalformedJwtException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {SignatureException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HttpError signatureException(SignatureException ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpError internalServerError(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return HttpError.create(#UPPER_CASE_APP_ABBREVIATIONError.INTERNAL_ERROR);
    }
}
