package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 23.09.2014
 */
public interface ExceptionInfoHandler {
    Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    default ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    default ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    default ErrorInfo handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        return logAndGetValidationErrorInfo(req, e.getBindingResult());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(BindException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    default ErrorInfo bindValidationError(HttpServletRequest req, BindingResult result) {
        return logAndGetValidationErrorInfo(req, result);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @Order(Ordered.LOWEST_PRECEDENCE)
    default ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true);
    }

    default ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        if (logException) {
            LOG.error("Exception at request " + req.getRequestURL(), e);
        } else {
            LOG.warn("Exception at request " + req.getRequestURL() + ": " + e.toString());
        }
        return new ErrorInfo(req.getRequestURL(), e);
    }

    default ErrorInfo logAndGetValidationErrorInfo(HttpServletRequest req, BindingResult result) {
        String[] details = result.getFieldErrors().stream()
                .map(fe -> fe.getField() + ' ' + fe.getDefaultMessage()).toArray(String[]::new);

        LOG.warn("Validation exception at request " + req.getRequestURL() + ": " + Arrays.toString(details));
        return new ErrorInfo(req.getRequestURL(), "ValidationException", details);
    }
}
