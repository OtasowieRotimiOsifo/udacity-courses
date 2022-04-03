package com.example.demo.utils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class ErrorUtils {

    public  static Map<String, String> getErrorMap(HttpServletResponse res, String message)  {
        res.setHeader("error", message);
        res.setStatus(FORBIDDEN.value());

        Map<String, String> error = new HashMap<>();
        error.put("error_message", message);

        res.setContentType(APPLICATION_JSON_VALUE);

        return error;
    }
}
