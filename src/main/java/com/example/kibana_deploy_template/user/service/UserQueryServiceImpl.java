package com.example.kibana_deploy_template.user.service;

import com.example.kibana_deploy_template.user.exceptions.UserDoesntExist;
import com.example.kibana_deploy_template.user.exceptions.UserListEmpty;
import com.example.kibana_deploy_template.user.model.User;
import com.example.kibana_deploy_template.user.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepo userRepo;

    public UserQueryServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            log.error("User with email {} not found", email);
            throw new UserDoesntExist("User with email " + email + " not found");
        }
        return user;
    }

    @Override
    public Optional<List<User>> findAllByGender(String gender) {
        Optional<List<User>> users = userRepo.findAllByGender(gender);
        if(users.isEmpty()){
            log.error("User list is empty");
            throw new UserListEmpty("User list is empty");
        }
        return users;
    }
}
