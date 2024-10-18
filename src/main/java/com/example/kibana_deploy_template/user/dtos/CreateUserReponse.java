package com.example.kibana_deploy_template.user.dtos;

import com.example.kibana_deploy_template.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateUserReponse {
    private User user;

    @Builder.Default
    private String message = "User created successfully";
}
