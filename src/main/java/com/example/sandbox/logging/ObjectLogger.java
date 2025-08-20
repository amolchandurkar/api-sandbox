package com.example.sandbox.logging;

import java.util.Set;

public interface ObjectLogger {
    void debugObject(Object obj);
    void setLogFields(Set<String> fields);
    void setDebugMode(boolean debugMode);
}
