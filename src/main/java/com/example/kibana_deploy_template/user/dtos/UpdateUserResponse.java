package com.example.kibana_deploy_template.user.dtos;

import com.example.kibana_deploy_template.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

    private User user;

    @Builder.Default
    private String message = "User updated successfully";
}
