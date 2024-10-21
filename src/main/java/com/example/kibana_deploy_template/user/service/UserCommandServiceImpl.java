package com.example.kibana_deploy_template.user.service;


import com.example.kibana_deploy_template.user.dtos.CreateUserReponse;
import com.example.kibana_deploy_template.user.dtos.CreateUserRequest;
import com.example.kibana_deploy_template.user.dtos.UpdateUserResponse;
import com.example.kibana_deploy_template.user.model.User;
import com.example.kibana_deploy_template.user.repository.UserRepo;
import com.example.kibana_deploy_template.user.system.logs.LogAction;
import com.example.kibana_deploy_template.user.system.logs.LogMessage;
import com.example.kibana_deploy_template.user.system.logs.LogStatus;
import com.example.kibana_deploy_template.user.system.logs.StructuredLogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
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
    private final StructuredLogger logger = new StructuredLogger(LoggerFactory.getLogger(UserCommandServiceImpl.class));


    @Override
    public CreateUserReponse createUser(CreateUserRequest createUserRequest) {
        Optional<User> user = userRepo.findByEmail(createUserRequest.getEmail());
        if (user.isPresent()) {
            logger.logBuilder()
                    .withMessage(LogMessage.USER_CREATION_FAILED)
                    .withField("email", createUserRequest.getEmail())
                    .withField("action", LogAction.CREATE_USER)
                    .withField("status", LogStatus.ALREADY_EXISTS)
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

        logger.logBuilder()
                .withMessage(LogMessage.USER_CREATED_SUCCESS)
                .withField("userId", savedUser.getId())
                .withField("email", savedUser.getEmail())
                .withField("action", LogAction.CREATE_USER)
                .withField("status", LogStatus.SUCCESS)
                .log();

        return CreateUserReponse.builder().user(savedUser).build();
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            logger.logBuilder()
                    .withMessage(LogMessage.USER_DELETION_FAILED)
                    .withField("email", email)
                    .withField("action", LogAction.DELETE_USER)
                    .withField("status", LogStatus.USER_NOT_FOUND)
                    .withLevel("ERROR")
                    .log();

            throw new UserDoesntExist("User with email " + email + " not found");
        }
        userRepo.delete(user.get());

        logger.logBuilder()
                .withMessage(LogMessage.USER_DELETED_SUCCESS)
                .withField("email", email)
                .withField("action", LogAction.DELETE_USER)
                .withField("status", LogStatus.SUCCESS)
                .log();
    }

    @Override
    public UpdateUserResponse updateUser(String email, CreateUserRequest createUserRequest) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            logger.logBuilder()
                    .withMessage(LogMessage.USER_UPDATE_FAILED)
                    .withField("email", email)
                    .withField("action", LogAction.UPDATE_USER)
                    .withField("status", LogStatus.USER_NOT_FOUND)
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

        logger.logBuilder()
                .withMessage(LogMessage.USER_UPDATED_SUCCESS)
                .withField("userId", savedUser.getId())
                .withField("email", savedUser.getEmail())
                .withField("action", LogAction.UPDATE_USER)
                .withField("status", LogStatus.SUCCESS)
                .log();

        return UpdateUserResponse.builder().user(savedUser).build();
    }
}
