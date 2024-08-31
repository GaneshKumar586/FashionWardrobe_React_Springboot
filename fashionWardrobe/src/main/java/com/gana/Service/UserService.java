package com.gana.Service;

import com.gana.exception.UserException;
import com.gana.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserByJwt(String jwt) throws UserException;

}
