package com.example.sandbox.architecture;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ArchUnit tests to enforce project architecture using externalized configuration.
 */
class ArchitectureTest {

    private static Properties archunitProps;
    private static JavaClasses importedClasses;

    @BeforeAll
    static void setup() throws IOException {
        archunitProps = new Properties();
        try (InputStream in = ArchitectureTest.class.getClassLoader().getResourceAsStream("archunit.properties")) {
            archunitProps.load(in);
        }
        importedClasses = new ClassFileImporter().importPackages(archunitProps.getProperty("base.package"));
    }

    @Test
    void controllersShouldResideInControllerPackage() {
        String suffix = archunitProps.getProperty("controller.suffix");
        String pkg = archunitProps.getProperty("controller.package");
        ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith(suffix)
            .should().resideInAPackage(".." + pkg + "..")
            .check(importedClasses);
    }

    @Test
    void servicesShouldResideInServicePackage() {
        String suffix = archunitProps.getProperty("service.suffix");
        String pkg = archunitProps.getProperty("service.package");
        ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith(suffix)
            .should().resideInAPackage(".." + pkg + "..")
            .check(importedClasses);
    }

    @Test
    void modelsShouldResideInModelPackage() {
        String suffix = archunitProps.getProperty("model.suffix");
        String pkg = archunitProps.getProperty("model.package");
        ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith(suffix)
            .should().resideInAPackage(".." + pkg + "..")
            .check(importedClasses);
    }

    @Test
    void loggersShouldResideInLoggingPackage() {
        String keyword = archunitProps.getProperty("logger.keyword");
        String pkg = archunitProps.getProperty("logger.package");
        ArchRuleDefinition.classes()
            .that().haveSimpleNameContaining(keyword)
            .should().resideInAPackage(".." + pkg + "..")
            .check(importedClasses);
    }
}
