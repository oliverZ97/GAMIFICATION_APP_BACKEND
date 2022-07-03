package app.restservice.apprestservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.User;
import app.restservice.apprestservice.Entities.UserAndToken;
import app.restservice.apprestservice.Entities.UserLogin;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.JwTAuthentication.JwtTokenUtil;
import app.restservice.apprestservice.Requests.JwtRequest;
import app.restservice.apprestservice.Services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public UserAndToken createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        if (!userService.getUserByEmail(authenticationRequest.getUsername()).equals(null)) {

            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            User user = userService.getUserByEmail(authenticationRequest.getUsername());
            UserAndToken thisToken = new UserAndToken(user, token);
            return thisToken;
        } else {
            throw new ResourceNotFoundException("there is no user with that username");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user, BindingResult result, HttpServletRequest request)
            throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            if (userService.checkifUsernameIsAlreadyTaken(user.getEmail())) {
                return ResponseEntity.badRequest().body("username is already taken");
            } else {
                userService.setUser(user);
                return ResponseEntity.status(HttpStatus.OK).body("user got saved sucessfully");
            }
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/users/get/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/users/getUserByUsername")
    public User getUserByUsername(@RequestBody UserLogin userRequest) {
        System.out.println(userRequest.getUsername());
        return userService.getUserByEmail(userRequest.getUsername());
    }

    @GetMapping("/users/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userRequest) {
        return userService.updateUser(userRequest, id);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
