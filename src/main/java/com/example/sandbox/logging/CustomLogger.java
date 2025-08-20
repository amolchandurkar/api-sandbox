package com.example.sandbox.logging;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomLogger allows configurable logging of object parameters, controlling PII/sensitive data exposure.
 * Usage:
 *   CustomLogger logger = new CustomLogger(MyClass.class);
 *   logger.setLogFields(Set.of("field1", "field2"));
 *   logger.debugObject(objAsMap);
 */
public class CustomLogger {
    private final Logger logger;
    private Set<String> logFields = new HashSet<>();
    private boolean debugMode = false;

    public CustomLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void setLogFields(Set<String> fields) {
        this.logFields = fields;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Log only configured fields from the object map in debug mode.
     */
    public void debugObject(Map<String, Object> objectMap) {
        if (!debugMode || !logger.isDebugEnabled()) return;
        StringBuilder sb = new StringBuilder("[DEBUG] ");
        for (String key : logFields) {
            if (objectMap.containsKey(key)) {
                sb.append(key).append("=").append(objectMap.get(key)).append(", ");
            }
        }
        logger.debug(sb.toString());
    }
}
