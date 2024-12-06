package org.factoriaf5.ecommerce.service;

import org.factoriaf5.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService{
    @Autowired
    UserRepository userRepository;
}
