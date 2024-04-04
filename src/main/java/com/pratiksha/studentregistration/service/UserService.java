package com.pratiksha.studentregistration.service;
import com.pratiksha.studentregistration.exception.ResourceNotFoundException;
import com.pratiksha.studentregistration.repository.UserRepository;
import com.pratiksha.studentregistration.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // You can add validation logic here before saving the user
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void updateUserById(Long userId,  User user){
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            existingUser.setId(userId);
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            userRepository.updateUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getId());

        }else {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
    }

    public void deleteUserById(Long userId){
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
        }else {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
    }
}