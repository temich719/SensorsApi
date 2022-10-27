package com.lab.sensors.allowedDTOValues;

import com.lab.sensors.dto.SensorDTO;
import org.springframework.stereotype.Component;

@Component
public class AllowedDTOValuesStorage {

    private static final String PRESSURE = "Pressure";
    private static final String VOLTAGE = "Voltage";
    private static final String TEMPERATURE = "Temperature";
    private static final String HUMIDITY = "Humidity";

    private static final String BAR = "bar";
    private static final String VOLTAGE_UNIT = "voltage";
    private static final String CELSIUS = "℃";
    private static final String PERCENT = "%";

    public boolean checkDTOType(String type) {
        return type.equals(PRESSURE) || type.equals(VOLTAGE) || type.equals(TEMPERATURE) || type.equals(HUMIDITY);
    }

    public boolean checkDTOUnit(String unit) {
        return unit.equals(BAR) || unit.equals(VOLTAGE_UNIT) || unit.equals(CELSIUS) || unit.equals(PERCENT);
    }

}
