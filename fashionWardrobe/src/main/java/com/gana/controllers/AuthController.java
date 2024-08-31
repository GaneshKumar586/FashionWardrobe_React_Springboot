package com.gana.controllers;

import com.gana.Service.CartService;
import com.gana.Service.CustomUserServiceImplementation;
import com.gana.config.JwtProvider;
import com.gana.exception.UserException;
import com.gana.model.Cart;
import com.gana.model.User;
import com.gana.repository.UserRepository;
import com.gana.request.LoginRequest;
import com.gana.response.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RequestMapping("/auth")
public class AuthController {
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomUserServiceImplementation customUserServiceImplementation;
    private UserRepository userRepository;
    private CartService cartService;

    public AuthController(UserRepository userRepository,JwtProvider jwtProvider,CustomUserServiceImplementation customUserServiceImplementation,PasswordEncoder passwordEncoder,CartService cartService){
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.customUserServiceImplementation =customUserServiceImplementation;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
//        String email = user.getEmail();
        System.out.println("hello");
        User isEmailExist = userRepository.findUserByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email is Already belongs to other existent user");

        }

        User userCreated = new User();
        userCreated.setEmail(email);
        userCreated.setPassword(passwordEncoder.encode(password));
        userCreated.setFirstName(firstName);
        userCreated.setLastName(lastName);

        User isUserSaved = userRepository.save(userCreated);
        Cart cart = cartService.createCart(isUserSaved);
        Authentication authentication = new UsernamePasswordAuthenticationToken(isUserSaved.getEmail(),isUserSaved.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Successfully created an account");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) throws UserException{
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =jwtProvider.generateToken(authentication);

//        AuthResponse authResponse = new AuthResponse(token,"Successfully loggedIn to the account");
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Successfully loggedIn to the account");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
///////// ///////////////////////////////////       DID BY ME          ///////////////////////////////
//        User existentUser = userRepository.findUserByEmail(username);
//        if(existentUser==null){
//
//            throw new UserException("Non-registered emailId..please signup first");
//        }
//        if(existentUser.getPassword() != passwordEncoder.encode(password)){
//
//            throw new UserException("Incorrect Password..please try again");
//        }
//
//
//        Authentication authentication= new UsernamePasswordAuthenticationToken(username,password);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token =jwtProvider.generateToken(authentication);
//
//        AuthResponse authResponse = new AuthResponse(token,"Successfully loggedIn to the account");
//        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
//  ////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username");
        }
        if(passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
