package com.progweb.frota.service;

import com.progweb.frota.model.User;
import com.progweb.frota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public List<User> findUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public void updateUser(Long id, User updateUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingUser.setName(updateUser.getName());
        existingUser.setRole(updateUser.getRole());
        existingUser.setEmail(updateUser.getEmail());
        existingUser.setDocument(updateUser.getDocument());
        existingUser.setPhone(updateUser.getPhone());
        existingUser.setUsername(updateUser.getUsername());
        
        
        if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
            existingUser.setPassword(updateUser.getPassword());
        }
        
        userRepository.save(existingUser);
    }

}