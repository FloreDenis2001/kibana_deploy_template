package com.example.kibana_deploy_template.user.service;

import com.example.kibana_deploy_template.user.dtos.CreateUserReponse;
import com.example.kibana_deploy_template.user.dtos.CreateUserRequest;
import com.example.kibana_deploy_template.user.dtos.UpdateUserResponse;
import com.example.kibana_deploy_template.user.model.User;
import com.example.kibana_deploy_template.user.repository.UserRepo;
import com.example.kibana_deploy_template.user.exceptions.UserAlreadyExists;
import com.example.kibana_deploy_template.user.exceptions.UserDoesntExist;
import com.example.kibana_deploy_template.user.utils.StructuredLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepo userRepo;
    private final StructuredLogger structuredLogger;

    public UserCommandServiceImpl(UserRepo userRepo, StructuredLogger structuredLogger) {
        this.userRepo = userRepo;
        this.structuredLogger = structuredLogger;
    }

    @Override
    public CreateUserReponse createUser(CreateUserRequest createUserRequest) {
        Optional<User> user = userRepo.findByEmail(createUserRequest.getEmail());
        if (user.isPresent()) {
            structuredLogger.logBuilder()
                    .withMessage("User with given email already exists")
                    .withField("email", createUserRequest.getEmail())
                    .withLevel("ERROR")
                    .log();
            throw new UserAlreadyExists("User with email " + createUserRequest.getEmail() + " already exists");
        }

        User newUser = User.builder()
                .name(createUserRequest.getName())
                .gender(createUserRequest.getGender())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .build();

        User savedUser = userRepo.saveAndFlush(newUser);

        structuredLogger.logBuilder()
                .withMessage("User created successfully")
                .withField("email", savedUser.getEmail())
                .withLevel("INFO")
                .log();

        return CreateUserReponse.builder().user(savedUser).build();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            structuredLogger.logBuilder()
                    .withMessage("User not found for deletion")
                    .withField("email", email)
                    .withLevel("ERROR")
                    .log();
            throw new UserDoesntExist("User with email " + email + " not found");
        }

        userRepo.delete(user.get());

        structuredLogger.logBuilder()
                .withMessage("User deleted successfully")
                .withField("email", email)
                .withLevel("INFO")
                .log();
    }

    @Override
    public UpdateUserResponse updateUser(String email, CreateUserRequest createUserRequest) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            structuredLogger.logBuilder()
                    .withMessage("User not found for update")
                    .withField("email", email)
                    .withLevel("ERROR")
                    .log();
            throw new UserDoesntExist("User with email " + email + " not found");
        }

        User updatedUser = User.builder()
                .name(createUserRequest.getName())
                .gender(createUserRequest.getGender())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .build();

        User savedUser = userRepo.saveAndFlush(updatedUser);

        structuredLogger.logBuilder()
                .withMessage("User updated successfully")
                .withField("email", savedUser.getEmail())
                .withLevel("INFO")
                .log();

        return UpdateUserResponse.builder().user(savedUser).build();
    }
}
