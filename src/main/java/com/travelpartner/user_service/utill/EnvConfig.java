package com.travelpartner.user_service.utill;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class EnvConfig {

    private final Dotenv dotenv = Dotenv.configure().load();

    public String getUserServiceBaseURL() {
        return dotenv.get("USER_SERVICE_BASE_URL");
    }
}
