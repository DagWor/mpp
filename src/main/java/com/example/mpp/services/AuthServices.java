package com.example.mpp.services;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.mpp.models.User;
import com.example.mpp.models.*;
import com.example.mpp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;


@Component
@Service
public class AuthServices {

        @Autowired
        private UserRepository userRepository;

        public User getCurrentUser(){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser= userRepository.findUserByUsername(auth.getName());
            return currentUser;
        }





}


