package com.lab.sensors.service;

import com.lab.sensors.dto.ResponseSensorDTO;
import com.lab.sensors.dto.SensorDTO;
import com.lab.sensors.exception.NoSuchIdException;
import com.lab.sensors.exception.ServiceException;

import java.util.List;

public interface SensorService {

    List<ResponseSensorDTO> getSensorsDTOList(int page, int size);

    List<ResponseSensorDTO> searchSensorDTOsByKeyWord(String keyword, int page, int size) throws ServiceException;

    void editSensor(long id, SensorDTO sensorDTO) throws NoSuchIdException;

    void deleteSensor(long id) throws NoSuchIdException;

    void addSensor(SensorDTO sensorDTO);

}
