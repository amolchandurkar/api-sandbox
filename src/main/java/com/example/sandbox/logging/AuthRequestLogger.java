package com.example.sandbox.logging;

import com.example.sandbox.model.AuthRequest;
// ...existing code...
import java.util.Set;

public class AuthRequestLogger {
    private final MapObjectLogger logger;

    public AuthRequestLogger(Set<String> logFields, boolean debugMode) {
        this.logger = new MapObjectLogger(AuthRequest.class, logFields, debugMode);
    }

    public void debug(AuthRequest request) {
        logger.debugObject(request.toMap());
    }
}
