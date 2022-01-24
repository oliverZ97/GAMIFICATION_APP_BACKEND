package app.restservice.apprestservice.Services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.restservice.apprestservice.CopyPropertiesofEntity;
import app.restservice.apprestservice.Entities.User;
import app.restservice.apprestservice.Exceptions.ResourceNotFoundException;
import app.restservice.apprestservice.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

    private CopyPropertiesofEntity copyPropertiesofEntity;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(),
                    new ArrayList<>());
        }
    }

    public User getUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> useroptional = userRepository.getUserByEmail(email);
        if (useroptional.isPresent()) {
            return useroptional.get();
        } else {
            throw new ResourceNotFoundException("User with email not found");
        }
    }

    public boolean checkIfEmailIsAlreadyTaken(String email) {
        return userRepository.getUserByEmail(email).isPresent();
      }

    public User getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new app.restservice.apprestservice.Exceptions.ResourceNotFoundException(
                    "User not found at id " + id.toString());
        }
    }

    public User setUser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public Page<User> getAllUser(Pageable pageable) {

        pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return userRepository.findAll(pageable);
    }

    public User updateUser(Long id, User userRequest) {
    
    User existingUser = getUser(id);
    copyPropertiesofEntity.copyNonNullProperties(userRequest, existingUser);
    return userRepository.save(existingUser);
    }

    public ResponseEntity<?> deleteUser(Long id) {
    return userRepository.findById(id).map(user -> {
    userRepository.delete(user);
    return ResponseEntity.ok().build();
    }).orElseThrow(() -> new ResourceNotFoundException("User not found with id "
    + id));
    }
}
