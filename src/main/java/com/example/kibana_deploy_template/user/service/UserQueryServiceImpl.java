package com.example.kibana_deploy_template.user.service;

import com.example.kibana_deploy_template.user.exceptions.UserDoesntExist;
import com.example.kibana_deploy_template.user.exceptions.UserListEmpty;
import com.example.kibana_deploy_template.user.model.User;
import com.example.kibana_deploy_template.user.repository.UserRepo;
import com.example.kibana_deploy_template.user.system.logs.LogAction;
import com.example.kibana_deploy_template.user.system.logs.LogMessage;
import com.example.kibana_deploy_template.user.system.logs.LogStatus;
import com.example.kibana_deploy_template.user.utils.StructuredLogger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepo userRepo;
    private final StructuredLogger logger;



    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            logger.logBuilder()
                    .withMessage(LogMessage.USER_NOT_FOUND)
                    .withField("email", email)
                    .withField("action", LogAction.FIND_BY_EMAIL)
                    .withField("status", LogStatus.USER_NOT_FOUND)
                    .withLevel("ERROR")
                    .log();

            throw new UserDoesntExist("User with email " + email + " not found");
        }

        // Log success
        logger.logBuilder()
                .withMessage(LogMessage.USER_FOUND_SUCCESS)
                .withField("userId", user.get().getId())
                .withField("email", email)
                .withField("action", LogAction.FIND_BY_EMAIL)
                .withField("status", LogStatus.SUCCESS)
                .log();

        return user;
    }

    @Override
    public Optional<List<User>> findAllByGender(String gender) {
        Optional<List<User>> users = userRepo.findAllByGender(gender);
        if (users.isEmpty()) {
            logger.logBuilder()
                    .withMessage(LogMessage.USER_LIST_EMPTY)
                    .withField("gender", gender)
                    .withField("action", LogAction.FIND_ALL_BY_GENDER)
                    .withField("status", LogStatus.USER_LIST_EMPTY)
                    .withLevel("ERROR")
                    .log();

            throw new UserListEmpty("User list is empty");
        }

        // Log success
        logger.logBuilder()
                .withMessage(LogMessage.USER_LIST_FOUND_SUCCESS)
                .withField("gender", gender)
                .withField("userCount", users.get().size())
                .withField("action", LogAction.FIND_ALL_BY_GENDER)
                .withField("status", LogStatus.SUCCESS)
                .log();

        return users;
    }
}
