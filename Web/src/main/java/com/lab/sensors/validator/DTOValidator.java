package com.lab.sensors.validator;

import com.lab.sensors.allowedDTOValues.AllowedDTOValuesStorage;
import com.lab.sensors.dto.SensorDTO;
import com.lab.sensors.exception.InvalidInputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Component
public class DTOValidator {

    private static final String ALLOWED_VALUES_MESSAGE_CONSTRAINT = "Type or unit has incorrect value";
    private static final String RANGE_FROM_BIGGER_OR_EQUAL_THAN_TO = "Range from is bigger than range to, or equal to it";

    private final AllowedDTOValuesStorage allowedDTOValuesStorage;

    @Autowired
    public DTOValidator(AllowedDTOValuesStorage allowedDTOValuesStorage) {
        this.allowedDTOValuesStorage = allowedDTOValuesStorage;
    }

    public void validate(SensorDTO sensorDTO, BindingResult bindingResult) throws InvalidInputDataException {
        bindingResultCheck(bindingResult);
        checkAllowedValues(sensorDTO.getType(), sensorDTO.getUnit());
        checkRange(sensorDTO.getRangeFrom(), sensorDTO.getRangeTo());
    }

    private void bindingResultCheck(BindingResult bindingResult) throws InvalidInputDataException {
        if (bindingResult.hasErrors()) {
            StringBuilder exceptionMessage = new StringBuilder();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                exceptionMessage.append(objectError.getDefaultMessage()).append(". ");
            }
            throw new InvalidInputDataException(exceptionMessage.toString());
        }
    }

    private void checkAllowedValues(String type, String unit) throws InvalidInputDataException {
        if (!allowedDTOValuesStorage.checkDTOType(type) || !allowedDTOValuesStorage.checkDTOUnit(unit)) {
            throw new InvalidInputDataException(ALLOWED_VALUES_MESSAGE_CONSTRAINT);
        }
    }

    private void checkRange(String rangeFrom, String rangeTo) throws InvalidInputDataException {
        if (Integer.parseInt(rangeFrom) >= Integer.parseInt(rangeTo)) {
            throw new InvalidInputDataException(RANGE_FROM_BIGGER_OR_EQUAL_THAN_TO);
        }
    }

}
