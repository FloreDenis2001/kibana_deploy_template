package com.example.kibana_deploy_template.user.web;


import com.example.kibana_deploy_template.user.dtos.CreateUserRequest;
import com.example.kibana_deploy_template.user.dtos.CreateUserReponse;
import com.example.kibana_deploy_template.user.dtos.UpdateUserResponse;
import com.example.kibana_deploy_template.user.model.User;
import com.example.kibana_deploy_template.user.service.UserCommandServiceImpl;
import com.example.kibana_deploy_template.user.service.UserQueryServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class UserControllerService {

    private final UserCommandServiceImpl userCommandService;
    private final UserQueryServiceImpl userQueryService;



    @PostMapping
    public ResponseEntity<CreateUserReponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserReponse response = userCommandService.createUser(createUserRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable String email,
            @RequestBody CreateUserRequest createUserRequest) {
        UpdateUserResponse response = userCommandService.updateUser(email, createUserRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userCommandService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userQueryService.findByEmail(email);
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<User>> getUsersByGender(@PathVariable String gender) {
        Optional<List<User>> users = userQueryService.findAllByGender(gender);
        return ResponseEntity.ok(users.get());
    }
}
