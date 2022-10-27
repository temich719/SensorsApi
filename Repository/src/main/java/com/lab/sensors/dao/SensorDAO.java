package com.lab.sensors.dao;

import com.lab.sensors.entity.Sensor;

import java.util.List;
import java.util.Optional;

public interface SensorDAO {

    List<Sensor> getSensorsList(int page, int size);

    List<Sensor> searchSensorsByKeyWord(String keyword);

    void editSensor(long id, Sensor sensor);

    void deleteSensor(long id);

    void addSensor(Sensor sensor);

    Optional<Sensor> getSensorById(long id);

}
