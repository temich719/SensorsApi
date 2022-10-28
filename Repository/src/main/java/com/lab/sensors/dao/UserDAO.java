package com.lab.sensors.dao;

import com.lab.sensors.entity.User;
import com.lab.sensors.exception.RepositoryException;

import java.util.Optional;

public interface UserDAO {

    Optional<User> getUserByLogin(String login);

    void saveUser(User user) throws RepositoryException;

}
