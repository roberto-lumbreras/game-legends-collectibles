package org.factoriaf5.ecommerce.service;

import org.factoriaf5.ecommerce.dto.UserDTO;
import org.factoriaf5.ecommerce.model.User;
import org.factoriaf5.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    public User findByUsername(String username){
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
    public void save(String username, UserDTO userDTO){
        User user = findByUsername(username);
        user.setAddress(userDTO.address());
        user.setEmail(userDTO.email());
        user.setPhoneNumber(userDTO.phoneNumber());
        repository.save(user);
    }
}
