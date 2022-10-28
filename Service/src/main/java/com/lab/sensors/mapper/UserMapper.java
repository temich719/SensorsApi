package com.lab.sensors.mapper;

import com.lab.sensors.dto.RoleUserDTO;
import com.lab.sensors.dto.UserDTO;
import com.lab.sensors.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private static final String USER_ROLE = "ROLE_Viewer";

    public User mapToUserFromDTO(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getLogin(), userDTO.getPassword(), USER_ROLE);
    }

    public UserDTO mapToDTOFromUser(User user) {
        return new UserDTO(user.getId(), user.getLogin(), user.getPassword());
    }

    public RoleUserDTO mapToRoleUserDTO(User user) {
        return new RoleUserDTO(user.getLogin(), user.getPassword(), user.getRole());
    }

}
