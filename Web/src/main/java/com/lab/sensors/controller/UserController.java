package com.lab.sensors.controller;

import com.lab.sensors.answer.AnswerMessage;
import com.lab.sensors.dto.RoleUserDTO;
import com.lab.sensors.dto.UserDTO;
import com.lab.sensors.exception.InvalidPasswordException;
import com.lab.sensors.exception.ServiceException;
import com.lab.sensors.model.AuthenticationRequest;
import com.lab.sensors.model.AuthenticationResponse;
import com.lab.sensors.security.jwtProvider.JwtProvider;
import com.lab.sensors.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController{

    private static final String NEW_USER_CODE = "90";
    private static final String NEW_USER_HAS_BEEN_CREATED = "New user has been created";

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(AnswerMessage answerMessage, UserService userService, JwtProvider jwtProvider) {
        super(answerMessage);
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AnswerMessage register(@RequestBody UserDTO userDTO) throws ServiceException {
        LOGGER.info("Registration...");
        userDTO.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        userService.saveUser(userDTO);
        setAnswerMessage(NEW_USER_HAS_BEEN_CREATED, HttpStatus.ACCEPTED.value() + NEW_USER_CODE, HttpStatus.ACCEPTED.toString());
        return answerMessage;
    }

    @PostMapping(value = "/signIn", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationResponse sighIn(@RequestBody AuthenticationRequest authenticationRequest) throws ServiceException, InvalidPasswordException {
        LOGGER.info("Login...");
        RoleUserDTO roleUserDTO = userService.getUserWithRole(authenticationRequest.getLogin());
        String token;
        if (BCrypt.checkpw(authenticationRequest.getPassword(), roleUserDTO.getPassword())){
            token = jwtProvider.generateToken(authenticationRequest.getLogin());
        } else {
            throw new InvalidPasswordException("Wrong password");
        }
        return new AuthenticationResponse(token, roleUserDTO.getRole());
    }

}
