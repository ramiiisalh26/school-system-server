package com.example.school.excption;

public class StatusCode {
    
    public static final int SUCCESS = 200;  //success

    public static final int INVALID_ARGUMENT = 400; // Bad request, e.g invalid parameter
    
    public static final int UNAUTHORIZED = 401; // username or password incorrect

    public static final int FORBIDDEN = 403; // No premission

    public static final int NOT_FOUND = 404; // Not Found

    public static final int INTERNAL_SERVER_ERROR = 500; // Server Internal error
}
