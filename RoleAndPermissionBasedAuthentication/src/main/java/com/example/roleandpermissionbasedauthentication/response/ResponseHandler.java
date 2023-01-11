package com.example.roleandpermissionbasedauthentication.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message,
                                                          HttpStatus status,
                                                          Object responseObj,
                                                          boolean success) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        map.put("success", success);

        return new ResponseEntity<>(map, status);

    }
}
