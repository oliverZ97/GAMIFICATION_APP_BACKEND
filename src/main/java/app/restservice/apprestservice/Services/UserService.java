package app.restservice.apprestservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesOfEntity;
import app.restservice.apprestservice.Entities.User;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private CopyPropertiesOfEntity copyPropertiesOfEntity;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public User getUserByEmail(String username) {
        Optional<User> useroptional = userRepository.getUserByUsername(username);
        if (useroptional.isPresent()) {
            System.out.println("present");
            return useroptional.get();
        } else {
            throw new ResourceNotFoundException("User with username not found");
        }
    }

    public boolean checkifUsernameIsAlreadyTaken(String username) {
        return userRepository.getUserByUsername(username).isPresent();
    }

    public User getUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("no question found at id" + id);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User setUser(User user) {

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    public User updateUser(User userRequest, long id) {
        User user = getUser(id);
        copyPropertiesOfEntity.copyNonNullProperties(userRequest, user);
        return userRepository.save(user);
    }

    public ResponseEntity<?> deleteUser(long id) {
        return userRepository.findById(id)
                .map(student -> {
                    userRepository.delete(student);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

}
