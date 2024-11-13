package com.example.kibana_deploy_template.user.system.logs;

public final class LogStatus {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String USER_NOT_FOUND = "userNotFound";
    public static final String ALREADY_EXISTS = "alreadyExists";
    public static final String USER_LIST_EMPTY = "userListEmpty";
    public static final String USER_FOUND_SUCCESS = "User found successfully";
    public static final String USER_LIST_FOUND_SUCCESS = "User list retrieved successfully";
    public static final String REQUEST_RECEIVED = "requestReceived";
    public static final String RESPONSE_SENT = "responseSent";
    private LogStatus() {
    }
}
