package com.lab.sensors.controller;

import com.lab.sensors.answer.AnswerMessage;
import com.lab.sensors.dto.ResponseSensorDTO;
import com.lab.sensors.dto.SensorDTO;
import com.lab.sensors.exception.InvalidInputDataException;
import com.lab.sensors.exception.NoSuchIdException;
import com.lab.sensors.exception.ServiceException;
import com.lab.sensors.model.SensorsCount;
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

import static com.lab.sensors.stringsStorage.ControllerStringsStorage.*;

@RestController
@RequestMapping("/sensors")
public class SensorsController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(SensorsController.class);

    private static final String HTTP_OK_CODE = HttpStatus.OK.value() + "";
    private static final String HTTP_OK_STATUS = HttpStatus.OK.toString();

    private final SensorService sensorService;
    private final DTOValidator validator;

    @Autowired
    public SensorsController(SensorService sensorService, AnswerMessage answerMessage, DTOValidator validator) {
        super(answerMessage);
        this.sensorService = sensorService;
        this.validator = validator;
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SensorsCount getSensorsCount() {
        LOGGER.info("Getting sensors count...");
        return new SensorsCount(sensorService.getDefaultPagesCount());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseSensorDTO> getSensorDTOList(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "4") int size) {
        LOGGER.info("Getting list of sensors...");
        return sensorService.getSensorsDTOList(page, size);
    }

    @GetMapping(value = "/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseSensorDTO> getSensorDTOListByKeyWord(@PathVariable String keyword,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "4") int size) throws ServiceException {
        LOGGER.info("Searching sensors by keyword...");
        return sensorService.searchSensorDTOsByKeyWord(keyword, page, size);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AnswerMessage addSensor(@Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult) throws InvalidInputDataException {
        LOGGER.info("Adding new sensor...");
        validator.validate(sensorDTO, bindingResult);
        sensorService.addSensor(sensorDTO);
        setAnswerMessage(SENSOR_ADDED_MESSAGE, HTTP_OK_CODE + ADD_CODE, HTTP_OK_STATUS);
        return answerMessage;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AnswerMessage deleteSensor(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Deleting sensor...");
        sensorService.deleteSensor(id);
        setAnswerMessage(DELETED, HTTP_OK_CODE + DELETE_CODE, HTTP_OK_STATUS);
        return answerMessage;
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AnswerMessage updateSensor(@PathVariable long id, @Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult) throws InvalidInputDataException, NoSuchIdException {
        LOGGER.info("Updating sensor");
        validator.validate(sensorDTO, bindingResult);
        sensorDTO.setId(id);
        sensorService.editSensor(id, sensorDTO);
        setAnswerMessage(UPDATED, HTTP_OK_CODE + UPDATED_CODE, HTTP_OK_STATUS);
        return answerMessage;
    }

}
