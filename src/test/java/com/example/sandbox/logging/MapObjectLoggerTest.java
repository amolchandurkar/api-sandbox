package com.example.sandbox.logging;

import org.junit.jupiter.api.Test;
// ...existing code...
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MapObjectLoggerTest {
    @Test
    void testDebugObjectLogsConfiguredFields() {
        MapObjectLogger logger = new MapObjectLogger(MapObjectLoggerTest.class, Set.of("field1", "field2"), true);
        Map<String, Object> obj = new HashMap<>();
        obj.put("field1", "value1");
        obj.put("field2", "value2");
        obj.put("field3", "value3");
        // Should log only field1 and field2
        assertDoesNotThrow(() -> logger.debugObject(obj));
    }

    @Test
    void testDebugObjectWithNullObject() {
        MapObjectLogger logger = new MapObjectLogger(MapObjectLoggerTest.class, Set.of("field1"), true);
        assertDoesNotThrow(() -> logger.debugObject(null));
    }

    @Test
    void testDebugObjectWithNonMapThrows() {
        MapObjectLogger logger = new MapObjectLogger(MapObjectLoggerTest.class, Set.of("field1"), true);
        assertThrows(IllegalArgumentException.class, () -> logger.debugObject("not a map"));
    }

    @Test
    void testDebugObjectNotInDebugMode() {
        MapObjectLogger logger = new MapObjectLogger(MapObjectLoggerTest.class, Set.of("field1"), false);
        Map<String, Object> obj = new HashMap<>();
        obj.put("field1", "value1");
        // Should not log anything, but should not throw
        assertDoesNotThrow(() -> logger.debugObject(obj));
    }

    @Test
    void testSetLogFieldsAndDebugMode() {
        MapObjectLogger logger = new MapObjectLogger(MapObjectLoggerTest.class, Set.of("field1"), false);
        logger.setLogFields(Set.of("field2"));
        logger.setDebugMode(true);
        Map<String, Object> obj = new HashMap<>();
        obj.put("field2", "value2");
        assertDoesNotThrow(() -> logger.debugObject(obj));
    }
}
