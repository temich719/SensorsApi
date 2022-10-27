package com.lab.sensors.service;

import com.lab.sensors.dto.ResponseSensorDTO;
import com.lab.sensors.dto.SensorDTO;

import java.util.List;

public interface SensorService {

    List<ResponseSensorDTO> getSensorsDTOList(int page, int size);

    SensorDTO searchSensorDTOsByKeyWord(String keyword);

    void editSensor(long id, SensorDTO sensorDTO);

    void deleteSensor(long id);

    void addSensor(SensorDTO sensorDTO);

}
