package com.example.sandbox.logging;

import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ...existing code...

/**
 * Implementation of ObjectLogger for Map-based objects.
 * Keeps logging logic out of model classes.
 */
public class MapObjectLogger implements ObjectLogger {
    private final Logger logger;
    private Set<String> logFields;
    private boolean debugMode = false;

    public MapObjectLogger(Class<?> clazz, Set<String> logFields, boolean debugMode) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.logFields = logFields;
        this.debugMode = debugMode;
    }

    @Override
    public void setLogFields(Set<String> fields) {
        this.logFields = fields;
    }

    @Override
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public void debugObject(Object obj) {
        if (obj == null) return;
        if (!(obj instanceof Map)) throw new IllegalArgumentException("Object must be a Map");
        if (!debugMode || !logger.isDebugEnabled()) return;
        Map<?, ?> objectMap = (Map<?, ?>) obj;
        StringBuilder sb = new StringBuilder("[DEBUG] ");
        for (String key : logFields) {
            if (objectMap.containsKey(key)) {
                sb.append(key).append("=").append(objectMap.get(key)).append(", ");
            }
        }
        logger.debug(sb.toString());
    }
}
