package com.lab.sensors.service;

import com.lab.sensors.dto.RoleUserDTO;
import com.lab.sensors.dto.UserDTO;
import com.lab.sensors.exception.ServiceException;

public interface UserService {

    UserDTO getUserByLogin(String login) throws ServiceException;

    RoleUserDTO getUserWithRole(String login) throws ServiceException;

    void saveUser(UserDTO userDTO) throws ServiceException;

}
