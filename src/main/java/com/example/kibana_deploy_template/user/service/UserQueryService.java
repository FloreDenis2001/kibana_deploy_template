package com.example.kibana_deploy_template.user.service;
import com.example.kibana_deploy_template.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {

    Optional<User> findByEmail(String email);

    Optional<List<User>> findAllByGender(String gender);
}
