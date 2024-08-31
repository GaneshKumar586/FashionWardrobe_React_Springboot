package com.gana.Service;

import com.gana.config.JwtProvider;
import com.gana.exception.UserException;
import com.gana.model.User;
import com.gana.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserServiceImplementation implements UserService{
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
//    public UserServiceImplementation(UserRepository userRepository,JwtProvider jwtProvider){
//        this.userRepository=userRepository;
//        this.jwtProvider=jwtProvider;
//    }
    @Override
    public User findUserById(Long userId) throws UserException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            throw new UserException("No such user with provided Id has been found "+userId);
        }
        return user.get();
    }
    @Override
    public User findUserByJwt(String jwt) throws UserException{
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findUserByEmail(email);
        if(user==null){
            throw new UserException("No such user with provided email has been found "+email);
        }
        return user;
    }
}
