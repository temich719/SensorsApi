package com.lab.sensors.mapper;

import com.lab.sensors.dto.ResponseSensorDTO;
import com.lab.sensors.dto.SensorDTO;
import com.lab.sensors.entity.Sensor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SensorMapper {

    private static final String DASH = "-";

    public Sensor mapToSensorFromDTO(SensorDTO sensorDTO) {
        String range = sensorDTO.getRangeFrom() + DASH + sensorDTO.getRangeTo();
        return new Sensor(sensorDTO.getId(), sensorDTO.getName(), sensorDTO.getModel(), sensorDTO.getType(),
                range, sensorDTO.getUnit(), sensorDTO.getLocation(), sensorDTO.getDescription());
    }

    public ResponseSensorDTO mapToDTOFromSensor(Sensor sensor) {
        return new ResponseSensorDTO(sensor.getId(), sensor.getName(), sensor.getModel(), sensor.getType(), sensor.getRange(),
                sensor.getUnit(), sensor.getLocation(), sensor.getDescription());
    }

    public List<Sensor> mapToSensorList(List<SensorDTO> sensorDTOS) {
        return sensorDTOS.stream().map(this::mapToSensorFromDTO).collect(Collectors.toList());
    }

    public List<ResponseSensorDTO> mapToDTOList(List<Sensor> sensors) {
        return sensors.stream().map(this::mapToDTOFromSensor).collect(Collectors.toList());
    }

}
