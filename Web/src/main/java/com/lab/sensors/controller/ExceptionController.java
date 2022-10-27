package com.lab.sensors.controller;

import com.lab.sensors.answer.AnswerMessage;
import com.lab.sensors.exception.InvalidInputDataException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionController extends AbstractController{

    private static final Logger LOGGER = Logger.getLogger(ExceptionController.class);

    private static final String INVALID_INPUT_DATA_CODE = "50";

    private final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;


    @Autowired
    public ExceptionController(AnswerMessage answerMessage) {
        super(answerMessage);
    }

    @ExceptionHandler(value = InvalidInputDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AnswerMessage handleInvalidInputDataException(InvalidInputDataException e) {
        LOGGER.error("Handle InvalidInputDataException");
        answerMessage.setMessage(e.getMessage());
        answerMessage.setStatus(internalServerError.toString());
        answerMessage.setCode(internalServerError.value() + INVALID_INPUT_DATA_CODE);
        return answerMessage;
    }

}
