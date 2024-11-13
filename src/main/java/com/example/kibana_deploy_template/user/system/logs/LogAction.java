package com.example.kibana_deploy_template.user.system.logs;


public final class LogAction {
    public static final String CREATE_USER = "createUser";
    public static final String DELETE_USER = "deleteUser";
    public static final String UPDATE_USER = "updateUser";
    public static final String FIND_BY_EMAIL = "findByEmail";
    public static final String FIND_ALL_BY_GENDER = "findAllByGender";
    // Add more as needed

    private LogAction() {
        // Private constructor to prevent instantiation
    }
}
