package com.lab.sensors.controller;

import com.lab.sensors.answer.AnswerMessage;
import com.lab.sensors.dto.ResponseSensorDTO;
import com.lab.sensors.dto.SensorDTO;
import com.lab.sensors.exception.InvalidInputDataException;
import com.lab.sensors.service.SensorService;
import com.lab.sensors.validator.DTOValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(SensorsController.class);

    private final SensorService sensorService;
    private final DTOValidator validator;

    @Autowired
    public SensorsController(SensorService sensorService, AnswerMessage answerMessage, DTOValidator validator) {
        super(answerMessage);
        this.sensorService = sensorService;
        this.validator = validator;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseSensorDTO> getSensorDTOList(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "4") int size) {
        LOGGER.info("Getting list of sensors...");
        return sensorService.getSensorsDTOList(page, size);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AnswerMessage addSensor(@Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult) throws InvalidInputDataException {
        LOGGER.info("Adding new sensor...");
        validator.validate(sensorDTO, bindingResult);
        sensorService.addSensor(sensorDTO);
        answerMessage.setMessage("New sensor was added");
        answerMessage.setCode(HttpStatus.OK.value() + "00");
        answerMessage.setStatus(HttpStatus.OK.toString());
        return answerMessage;
    }

}
