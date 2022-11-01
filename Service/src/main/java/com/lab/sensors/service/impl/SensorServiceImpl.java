package com.lab.sensors.service.impl;

import com.lab.sensors.dao.SensorDAO;
import com.lab.sensors.dto.ResponseSensorDTO;
import com.lab.sensors.dto.SensorDTO;
import com.lab.sensors.entity.Sensor;
import com.lab.sensors.exception.NoSuchIdException;
import com.lab.sensors.exception.RepositoryException;
import com.lab.sensors.exception.ServiceException;
import com.lab.sensors.mapper.SensorMapper;
import com.lab.sensors.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lab.sensors.stringsStorage.ServiceStringsStorage.*;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ResponseSensorDTO> searchSensorDTOsByKeyWord(String keyword, int page, int size) throws ServiceException {
        List<Sensor> sensors = sensorDAO.searchSensorsByKeyWord(keyword, page, size).orElseThrow(() -> new ServiceException(NO_MATCH_SENSORS));
        return sensorMapper.mapToDTOList(sensors);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editSensor(long id, SensorDTO sensorDTO) throws NoSuchIdException {
        try {
            sensorDAO.editSensor(id, sensorMapper.mapToSensorFromDTO(sensorDTO));
        } catch (RepositoryException e) {
            throw new NoSuchIdException(THERE_IS_NO_SUCH_ID);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSensor(long id) throws NoSuchIdException {
        try {
            sensorDAO.deleteSensor(id);
        } catch (RepositoryException e) {
            throw new NoSuchIdException(THERE_IS_NO_SUCH_ID);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addSensor(SensorDTO sensorDTO) {
        sensorDAO.addSensor(sensorMapper.mapToSensorFromDTO(sensorDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public long getDefaultPagesCount() {
        long sensorsCount = sensorDAO.getSensorsCount();
        return calculatePageCountBySensorsCount(sensorsCount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public long getSensorsCount() {
        return sensorDAO.getSensorsCount();
    }

    private long calculatePageCountBySensorsCount(long sensorsCount) {
        long pages;
        if (sensorsCount % 4 == 0) {
            pages = sensorsCount / 4;
        } else {
            pages = sensorsCount / 4;
            long diff = sensorsCount - pages * 4;
            if (pages == 0) {
                pages = 1;
            } else if (diff > 0) {
                pages++;
            }
        }
        return pages;
    }
}
