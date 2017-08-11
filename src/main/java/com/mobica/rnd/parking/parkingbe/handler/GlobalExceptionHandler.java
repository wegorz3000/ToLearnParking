package com.mobica.rnd.parking.parkingbe.handler;

import com.mobica.rnd.parking.parkingbe.exception.CarException;
import com.mobica.rnd.parking.parkingbe.exception.MarkAvailableSlotsException;
import com.mobica.rnd.parking.parkingbe.exception.ParkingNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.env.Environment;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@PropertySource("classpath:ValidationMessages.properties")
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private Environment env;

    @Autowired
    public GlobalExceptionHandler(Environment env) {
        this.env = env;
    }

    @Value("${msg.validation.json.syntax.wrong}")
    private String wrongJsonSyntaxError;

    @Value("${msg.validation.parking.notexist}")
    private String parkingNotExistError;

    @Value("${msg.validation.wrongparameters}")
    private String wrongParametersError;

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleWebExchangeBindException(WebExchangeBindException e) {
        List<FieldError> errors = e.getFieldErrors();
        Map<String, String> response = new HashMap<>();
        for (FieldError fieldError : errors) {
            response.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Map<String, String> response = new HashMap<>();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            response.put("Wrong_parameters", constraintViolation.getMessage());
        }
        return response;
    }

    @ExceptionHandler(ParkingNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleParkingNotFoundException(ParkingNotFoundException e) {
        return buildResponseMap("not_found_error", parkingNotExistError);
    }

    @ExceptionHandler({IllegalStateException.class, DecodingException.class, MappingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleIllegalStateDecodingOrMappingException(Exception e) {
        return buildResponseMap("JSONSyntaxError", wrongJsonSyntaxError);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleTypeMismatchException(TypeMismatchException e) {
        return buildResponseMap("Wrong_parameters", wrongParametersError);
    }

    @ExceptionHandler(MarkAvailableSlotsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMarkAvailableSlotsException(MarkAvailableSlotsException e) {
        return buildResponseMap("Wrong_parameters", env.getProperty(e.getCode().getDescriptionProperty()));
    }

    @ExceptionHandler(CarException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleCarException(CarException e) {
        return buildResponseMap("Wrong_parameters", env.getProperty(e.getCode().getDescriptionProperty()));
    }

    private Map<String, String> buildResponseMap(String key, String value) {
        Map<String, String> response = new HashMap<>();
        response.put(key, value);
        return response;
    }
}
