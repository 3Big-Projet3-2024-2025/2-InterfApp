package be.helha.interf_app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;

import be.helha.interf_app.Model.LoginRequest;
import be.helha.interf_app.Model.User;
import be.helha.interf_app.Repository.UserRepository;
import be.helha.interf_app.security.JwtUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    AuthenticationManager authenticationManager;

    public User saveUser(User user) {
        user.setRoles("ROLE_User");
        return userRepository.save(user);
    }

    
    public LoginRequest login(LoginRequest loginRequest){
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (!userOptional.isPresent()){return null;}

        User userValues = userOptional.get();

        if(!userValues.getPassword().equals(loginRequest.getPassword())){return null;}

        loginRequest.setPassword("");

        loginRequest.setToken(JwtUtil.generateToken(userValues));
        
        return loginRequest; 
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
