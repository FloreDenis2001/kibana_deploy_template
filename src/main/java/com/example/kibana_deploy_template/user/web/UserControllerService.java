package com.example.kibana_deploy_template.user.web;

import com.example.kibana_deploy_template.user.dtos.CreateUserReponse;
import com.example.kibana_deploy_template.user.dtos.CreateUserRequest;
import com.example.kibana_deploy_template.user.dtos.UpdateUserResponse;
import com.example.kibana_deploy_template.user.model.User;
import com.example.kibana_deploy_template.user.service.UserCommandServiceImpl;
import com.example.kibana_deploy_template.user.service.UserQueryServiceImpl;
import com.example.kibana_deploy_template.user.system.logs.LogAction;
import com.example.kibana_deploy_template.user.system.logs.LogStatus;
import com.example.kibana_deploy_template.user.system.logs.LogMessage;
import com.example.kibana_deploy_template.user.utils.StructuredLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@AllArgsConstructor
public class UserControllerService {

    private final UserCommandServiceImpl userCommandService;
    private final UserQueryServiceImpl userQueryService;
    private final StructuredLogger logger;

    @PostMapping
    public ResponseEntity<CreateUserReponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        logger.logBuilder()
                .withMessage(LogMessage.CREATE_USER_REQUEST_RECEIVED)
                .withField("action", LogAction.CREATE_USER)
                .withField("status", LogStatus.REQUEST_RECEIVED)
                .log();

        CreateUserReponse response = userCommandService.createUser(createUserRequest);

        logger.logBuilder()
                .withMessage(LogMessage.CREATE_USER_RESPONSE_SENT)
                .withField("action", LogAction.CREATE_USER)
                .withField("status", LogStatus.RESPONSE_SENT)
                .log();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable String email,
            @RequestBody CreateUserRequest createUserRequest) {
        logger.logBuilder()
                .withMessage(LogMessage.UPDATE_USER_REQUEST_RECEIVED)
                .withField("action", LogAction.UPDATE_USER)
                .withField("email", email)
                .withField("status", LogStatus.REQUEST_RECEIVED)
                .log();

        UpdateUserResponse response = userCommandService.updateUser(email, createUserRequest);

        logger.logBuilder()
                .withMessage(LogMessage.UPDATE_USER_RESPONSE_SENT)
                .withField("action", LogAction.UPDATE_USER)
                .withField("email", email)
                .withField("status", LogStatus.RESPONSE_SENT)
                .log();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        logger.logBuilder()
                .withMessage(LogMessage.DELETE_USER_REQUEST_RECEIVED)
                .withField("action", LogAction.DELETE_USER)
                .withField("email", email)
                .withField("status", LogStatus.REQUEST_RECEIVED)
                .log();

        userCommandService.deleteUser(email);

        logger.logBuilder()
                .withMessage(LogMessage.DELETE_USER_RESPONSE_SENT)
                .withField("action", LogAction.DELETE_USER)
                .withField("email", email)
                .withField("status", LogStatus.RESPONSE_SENT)
                .log();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        logger.logBuilder()
                .withMessage(LogMessage.GET_USER_BY_EMAIL_REQUEST_RECEIVED)
                .withField("action", LogAction.FIND_BY_EMAIL)
                .withField("email", email)
                .withField("status", LogStatus.REQUEST_RECEIVED)
                .log();

        Optional<User> user = userQueryService.findByEmail(email);

        logger.logBuilder()
                .withMessage(LogMessage.GET_USER_BY_EMAIL_RESPONSE_SENT)
                .withField("action", LogAction.FIND_BY_EMAIL)
                .withField("email", email)
                .withField("status", LogStatus.RESPONSE_SENT)
                .log();

        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<User>> getUsersByGender(@PathVariable String gender) {
        logger.logBuilder()
                .withMessage(LogMessage.GET_USERS_BY_GENDER_REQUEST_RECEIVED)
                .withField("action", LogAction.FIND_ALL_BY_GENDER)
                .withField("gender", gender)
                .withField("status", LogStatus.REQUEST_RECEIVED)
                .log();

        Optional<List<User>> users = userQueryService.findAllByGender(gender);

        logger.logBuilder()
                .withMessage(LogMessage.GET_USERS_BY_GENDER_RESPONSE_SENT)
                .withField("action", LogAction.FIND_ALL_BY_GENDER)
                .withField("gender", gender)
                .withField("status", LogStatus.RESPONSE_SENT)
                .log();

        return ResponseEntity.ok(users.get());
    }
}
