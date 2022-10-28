package com.lab.sensors.service.impl;

import com.lab.sensors.dao.UserDAO;
import com.lab.sensors.dto.RoleUserDTO;
import com.lab.sensors.dto.UserDTO;
import com.lab.sensors.entity.User;
import com.lab.sensors.exception.RepositoryException;
import com.lab.sensors.exception.ServiceException;
import com.lab.sensors.mapper.UserMapper;
import com.lab.sensors.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String SUCH_USER_ALREADY_EXISTS = "Such user is already exist!";
    private static final String USER_DOESNT_EXIST = "User with the given name doesn't exist";

    private final UserDAO userDAO;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserDTO getUserByLogin(String login) throws ServiceException {
        Optional<User> optionalUser = userDAO.getUserByLogin(login);
        if (optionalUser.isPresent()) {
            return userMapper.mapToDTOFromUser(optionalUser.get());
        } else {
            throw new ServiceException(USER_DOESNT_EXIST);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUser(UserDTO userDTO) throws ServiceException {
        try {
            userDAO.saveUser(userMapper.mapToUserFromDTO(userDTO));
        } catch (RepositoryException e) {
            throw new ServiceException(SUCH_USER_ALREADY_EXISTS);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoleUserDTO getUserWithRole(String login) throws ServiceException {
        return userMapper.mapToRoleUserDTO(userDAO.getUserByLogin(login).orElseThrow(() -> new ServiceException(USER_DOESNT_EXIST)));
    }
}
