package com.lab.sensors.dao;

import com.lab.sensors.entity.Sensor;
import com.lab.sensors.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface SensorDAO {

    List<Sensor> getSensorsList(int page, int size);

    Optional<List<Sensor>> searchSensorsByKeyWord(String keyword, int page, int size);

    void editSensor(long id, Sensor sensor) throws RepositoryException;

    void deleteSensor(long id) throws RepositoryException;

    void addSensor(Sensor sensor);

    Optional<Sensor> getSensorById(long id);

}
