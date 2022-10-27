package com.lab.sensors.controller;

import com.lab.sensors.answer.AnswerMessage;
import com.lab.sensors.exception.InvalidInputDataException;
import com.lab.sensors.exception.NoSuchIdException;
import com.lab.sensors.exception.ServiceException;
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
    private static final String SERVICE_EXCEPTION_CODE = "51";
    private static final String NO_SUCH_ID_CODE = "52";
    private static final String INTERNAL_SERVER_ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    private static final String INTERNAL_SERVER_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value() + "";

    @Autowired
    public ExceptionController(AnswerMessage answerMessage) {
        super(answerMessage);
    }

    @ExceptionHandler(value = InvalidInputDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AnswerMessage handleInvalidInputDataException(InvalidInputDataException e) {
        LOGGER.error("Handle InvalidInputDataException");
        setAnswerMessage(e.getMessage(), INTERNAL_SERVER_ERROR_CODE + INVALID_INPUT_DATA_CODE, INTERNAL_SERVER_ERROR_STATUS);
        return answerMessage;
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AnswerMessage handleServiceException(ServiceException e) {
        LOGGER.error("Handle ServiceException");
        setAnswerMessage(e.getMessage(), INTERNAL_SERVER_ERROR_CODE + SERVICE_EXCEPTION_CODE, INTERNAL_SERVER_ERROR_STATUS);
        return answerMessage;
    }

    @ExceptionHandler(value = NoSuchIdException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AnswerMessage handleNoSuchIdException(NoSuchIdException e) {
        LOGGER.error("Handle NoSuchIdException");
        setAnswerMessage(e.getMessage(), INTERNAL_SERVER_ERROR_CODE + NO_SUCH_ID_CODE, INTERNAL_SERVER_ERROR_STATUS);
        return answerMessage;
    }

}
