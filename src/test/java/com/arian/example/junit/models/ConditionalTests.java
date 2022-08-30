package com.arian.example.junit.models;

import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

/**
 * Conditional Tests for execution in specific contexts such as OS, Java versions, etc.
 * System Properties and Environment Variables.
 */
class ConditionalTests {

   // ENVIRONMENT VARIABLES
   @Nested
   class EnvironmentVariablesTest {

      @Test
      void printEnvironmentVariables() {
         Map<String, String> envVars = System.getenv();
         envVars.forEach((k, v) -> System.out.println(k + "=" + v));
      }

      @Test
      @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-1.8.*")
      void testJavaHome() {
         // Test logic if JAVA_HOME matches
      }

      @Test
      @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
      void testEnvironment() {
         // Test logic if ENVIRONMENT matches "dev"
      }
   }

   // SYSTEM PROPERTIES
   @Nested
   class SystemPropertiesTest {

      @Test
      void printSystemProperties() {
         Properties properties = System.getProperties();
         properties.forEach((k, v) -> System.out.println(k + ":" + v));
      }

      @Test
      @EnabledIfSystemProperty(named = "java.version", matches = "11.0.12")
      void testJavaVersion1() {
         // Test logic for specific Java version 11.0.12
      }

      @Test
      @EnabledIfSystemProperty(named = "java.version", matches = ".*11.*")
      void testJavaVersion2() {
         // Test logic for any Java 11 version
      }

      @Test
      @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
      void testOnlyX64() {
         // Test logic for 64-bit architecture
      }

      @Test
      @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
      void testNoX32() {
         // Test logic excluding 32-bit architecture
      }

      @Test
      @EnabledIfSystemProperty(named = "user.name", matches = "ArianP1")
      void testUserName() {
         // Test logic for a specific username
      }

      @Test
      @EnabledIfSystemProperty(named = "ENV", matches = "dev")
      void testDevEnvironment() {
         // Test logic if system property ENV is "dev"
      }

   }

   // OPERATING SYSTEM
   @Nested
   class OperatingSystemTest {

      @Test
      @EnabledOnOs(OS.WINDOWS)
      void testOnlyWindows() {
         // Test logic only for Windows OS
      }

      @Test
      @EnabledOnOs({ OS.LINUX, OS.MAC })
      void testOnlyLinuxOrMac() {
         // Test logic only for Linux or Mac OS
      }

      @Test
      @DisabledOnOs(OS.WINDOWS)
      void testNotWindows() {
         // Test logic excluding Windows OS
      }
   }

   @Nested
   class JavaVersionTest {

      @Test
      @EnabledOnJre(JRE.JAVA_8)
      void testOnlyJDK8() {
         // Test logic only for JDK 8
      }

      @Test
      @EnabledOnJre(JRE.JAVA_11)
      void testOnlyJDK11() {
         // Test logic only for JDK 11
      }

      @Test
      @DisabledOnJre(JRE.JAVA_11)
      void testNoJDK11() {
         // Test logic excluding JDK 11
      }
   }
}
