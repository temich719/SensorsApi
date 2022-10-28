package com.lab.sensors.dao.impl;

import com.lab.sensors.dao.UserDAO;
import com.lab.sensors.entity.User;
import com.lab.sensors.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

import static com.lab.sensors.stringStorage.RepositoryStringStorage.*;

@Repository
public class UserDAOImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get(LOGIN), login));
        Query<User> query = session.createQuery(criteriaQuery);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void saveUser(User user) throws RepositoryException {
        try {
            getUserByLogin(user.getLogin());
            throw new RepositoryException("Such user is already exist!");
        } catch (NoResultException e) {
            sessionFactory.getCurrentSession().save(user);
        }
    }
}
