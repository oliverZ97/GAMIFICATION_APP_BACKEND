package app.restservice.apprestservice.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import app.restservice.apprestservice.Entities.User;
import app.restservice.apprestservice.Entities.UserandToken;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.JwTAuthentication.JwtRequest;
import app.restservice.apprestservice.JwTAuthentication.JwtTokenUtil;
import app.restservice.apprestservice.Services.UserService;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public UserandToken createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        if( !userService.getUserByEmail(authenticationRequest.getUserName()).equals(null)){
        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPasswordHash());
        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUserName());
        final String token = jwtTokenUtil.generateToken(userDetails);
        User user = userService.getUserByEmail(authenticationRequest.getUserName());
        return new UserandToken(user, token);
        }else{
            throw new ResourceNotFoundException("there is no user with that username");
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> saveUser(@RequestBody @Valid User user
            , BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            if (userService.checkIfEmailIsAlreadyTaken(user.getEmail())) {
                return ResponseEntity.badRequest().body("username is already taken");
            } else {
                userService.setUser(user);
                return ResponseEntity.ok().body("neuer User wurde registriert");
            }
        }
    }

    private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/")
    public String index() {
		return "Greetings from User!";
	}

    @GetMapping("/user/get/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

      @GetMapping("/user/getAll")
    public Page<User> getAllUser(Pageable pageable) {
        return userService.getAllUser(pageable);
    }

    @PostMapping("/user/set")
    public ResponseEntity<String> setUser(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        } else {
            userService.setUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("User wurde erfolgreich gespeichert");
        }
    }

    @PutMapping("/user/update/{id}")
    public User updateTest(@PathVariable Long id, @RequestBody User userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}

