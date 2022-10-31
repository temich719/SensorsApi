package com.lab.sensors.dao.impl;

import com.lab.sensors.dao.SensorDAO;
import com.lab.sensors.entity.Sensor;
import com.lab.sensors.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

import static com.lab.sensors.stringStorage.RepositoryStringStorage.*;

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
    public Optional<List<Sensor>> searchSensorsByKeyWord(String keyword, int page, int size) {
        Optional<List<Sensor>> optionalSensors;
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Sensor> criteriaQuery = criteriaBuilder.createQuery(Sensor.class);
            Root<Sensor> sensorRoot = criteriaQuery.from(Sensor.class);
            ParameterExpression<String> parameterExpression = criteriaBuilder.parameter(String.class);
            Predicate likeName = criteriaBuilder.like(sensorRoot.get(NAME), parameterExpression);
            Predicate likeModel = criteriaBuilder.like(sensorRoot.get(MODEL), parameterExpression);
            Predicate likeType = criteriaBuilder.like(sensorRoot.get(TYPE), parameterExpression);
            Predicate likeRange = criteriaBuilder.like(sensorRoot.get(RANGE), parameterExpression);
            Predicate likeUnit = criteriaBuilder.like(sensorRoot.get(UNIT), parameterExpression);
            Predicate likeLocation = criteriaBuilder.like(sensorRoot.get(LOCATION), parameterExpression);
            Predicate likeDescription = criteriaBuilder.like(sensorRoot.get(DESCRIPTION), parameterExpression);
            Predicate orCondition = criteriaBuilder.or(likeName, likeModel, likeType, likeRange, likeUnit, likeLocation, likeDescription);
            criteriaQuery.select(sensorRoot).where(orCondition);
            Query<Sensor> query = session.createQuery(criteriaQuery);
            query.setParameter(parameterExpression, PERCENT + keyword + PERCENT);
            List<Sensor> sensors = query.setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
            optionalSensors = Optional.of(sensors);
        } catch (NoResultException e) {
            optionalSensors = Optional.empty();
        }
        return optionalSensors;
    }

    @Override
    public void editSensor(long id, Sensor sensor) throws RepositoryException {
        Optional<Sensor> optionalSensor = getSensorById(id);
        if (optionalSensor.isPresent()) {
            Sensor sessionSensor = optionalSensor.get();
            sessionFactory.getCurrentSession().detach(sessionSensor);
            sessionFactory.getCurrentSession().update(sensor);
        } else {
            throw new RepositoryException();
        }
    }

    @Override
    public void deleteSensor(long id) throws RepositoryException {
        Optional<Sensor> sensor = getSensorById(id);
        if (sensor.isPresent()) {
            sessionFactory.getCurrentSession().delete(sensor.get());
        } else {
            throw new RepositoryException();
        }
    }

    @Override
    public void addSensor(Sensor sensor) {
        sessionFactory.getCurrentSession().save(sensor);
    }

    @Override
    public Optional<Sensor> getSensorById(long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Sensor.class, id));
    }

    @Override
    public long getSensorsCount() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Sensor> sensorRoot = criteriaQuery.from(Sensor.class);
        return session.createQuery(criteriaQuery.select(criteriaBuilder.count(sensorRoot))).getSingleResult();
    }
}
