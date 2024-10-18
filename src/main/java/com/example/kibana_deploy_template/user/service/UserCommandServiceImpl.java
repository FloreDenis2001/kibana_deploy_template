package com.example.kibana_deploy_template.user.service;


import com.example.kibana_deploy_template.user.dtos.CreateUserReponse;
import com.example.kibana_deploy_template.user.dtos.CreateUserRequest;
import com.example.kibana_deploy_template.user.dtos.UpdateUserResponse;
import com.example.kibana_deploy_template.user.model.User;
import com.example.kibana_deploy_template.user.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.kibana_deploy_template.user.exceptions.UserAlreadyExists;
import com.example.kibana_deploy_template.user.exceptions.UserDoesntExist;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepo userRepo;

    public UserCommandServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public CreateUserReponse createUser(CreateUserRequest createUserRequest) {
        Optional<User> user = userRepo.findByEmail(createUserRequest.getEmail());
        if (user.isPresent()) {
            log.error("User with email {} already exist", createUserRequest.getEmail());
            throw new UserAlreadyExists("User with email " + createUserRequest.getEmail() + " already exist");
        }
        User newUser = User.builder()
                .name(createUserRequest.getName())
                .gender(createUserRequest.getGender())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .build();

        User savedUser = userRepo.saveAndFlush(newUser);

        return CreateUserReponse.builder().user(savedUser).build();
    }


    @Transactional
    @Override
    public void deleteUser(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            log.error("User with email {} not found", email);
            throw new UserDoesntExist("User with email " + email + " not found");
        }
        userRepo.delete(user.get());
    }

    @Override
    public UpdateUserResponse updateUser(String email, CreateUserRequest createUserRequest) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            log.error("User with email {} not found", email);
            throw new UserDoesntExist("User with email " + email + " not found");
        }
        User updatedUser = User.builder()
                .name(createUserRequest.getName())
                .gender(createUserRequest.getGender())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .build();

        User savedUser = userRepo.saveAndFlush(updatedUser);

        return UpdateUserResponse.builder().user(savedUser).build();
    }
}
