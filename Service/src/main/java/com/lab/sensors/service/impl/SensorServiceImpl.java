package com.lab.sensors.service.impl;

import com.lab.sensors.dao.SensorDAO;
import com.lab.sensors.dto.ResponseSensorDTO;
import com.lab.sensors.dto.SensorDTO;
import com.lab.sensors.mapper.SensorMapper;
import com.lab.sensors.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

    private final SensorDAO sensorDAO;
    private final SensorMapper sensorMapper;

    @Autowired
    public SensorServiceImpl(SensorDAO sensorDAO, SensorMapper sensorMapper) {
        this.sensorDAO = sensorDAO;
        this.sensorMapper = sensorMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ResponseSensorDTO> getSensorsDTOList(int page, int size) {
        return sensorMapper.mapToDTOList(sensorDAO.getSensorsList(page, size));
    }

    @Override
    public SensorDTO searchSensorDTOsByKeyWord(String keyword) {
        return null;
    }

    @Override
    public void editSensor(long id, SensorDTO sensorDTO) {

    }

    @Override
    public void deleteSensor(long id) {

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addSensor(SensorDTO sensorDTO) {
        sensorDAO.addSensor(sensorMapper.mapToSensorFromDTO(sensorDTO));
    }
}
