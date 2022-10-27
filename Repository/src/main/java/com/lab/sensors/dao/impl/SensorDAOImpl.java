package com.lab.sensors.dao.impl;

import com.lab.sensors.dao.SensorDAO;
import com.lab.sensors.entity.Sensor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class SensorDAOImpl implements SensorDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public SensorDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Sensor> getSensorsList(int page, int size) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Sensor> criteriaQuery = criteriaBuilder.createQuery(Sensor.class);
        Root<Sensor> sensorRoot = criteriaQuery.from(Sensor.class);
        criteriaQuery.select(sensorRoot);
        Query<Sensor> sensorQuery = session.createQuery(criteriaQuery);
        sensorQuery.setFirstResult((page - 1) * size);
        sensorQuery.setMaxResults(size);
        return sensorQuery.getResultList();
    }

    @Override
    public List<Sensor> searchSensorsByKeyWord(String keyword) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Sensor> criteriaQuery = criteriaBuilder.createQuery(Sensor.class);
        return null;
    }

    @Override
    public void editSensor(long id, Sensor sensor) {
        sessionFactory.getCurrentSession().update(sensor);
    }

    @Override
    public void deleteSensor(long id) {
        Optional<Sensor> sensor = getSensorById(id);
        sensor.ifPresent(value -> sessionFactory.getCurrentSession().delete(value));
    }

    @Override
    public void addSensor(Sensor sensor) {
        sessionFactory.getCurrentSession().save(sensor);
    }

    @Override
    public Optional<Sensor> getSensorById(long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Sensor.class, id));
    }
}
