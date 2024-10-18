package com.example.kibana_deploy_template.user.service;

import com.example.kibana_deploy_template.user.dtos.CreateUserReponse;
import com.example.kibana_deploy_template.user.dtos.CreateUserRequest;
import com.example.kibana_deploy_template.user.dtos.UpdateUserResponse;

public interface UserCommandService {
    CreateUserReponse createUser(CreateUserRequest createUserRequest);

    void deleteUser(String email);

    UpdateUserResponse updateUser(String email, CreateUserRequest createUserRequest);
}
