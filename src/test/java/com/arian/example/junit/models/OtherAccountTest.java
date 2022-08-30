package com.arian.example.junit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OtherAccountTest extends AccountTest {

   @Tag("param")
   @ParameterizedTest(name = "number {index} running with value {0} - {argumentsWithNames}")
   @CsvFileSource(resources = "/data2.csv")
      // The file should be added inside the resources folder
   void testDebitAccountParameterizedFileCsv2(String balance, String amount, String expected, String actual) {
      System.out.printf("balance: %s, amount: %s, expectedName: %s, actualName: %s%n", balance, amount, expected, actual);
      account.setBalance(new BigDecimal(balance));
      account.setPerson(actual);

      account.debit(new BigDecimal(amount));

      assertNotNull(account.getBalance());
      assertNotNull(account.getPerson());

      assertEquals(expected, actual);
      assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
   }

   @ParameterizedTest(name = "number {index} running with value {0} - {argumentsWithNames}")
   @MethodSource("amountList")
   void testDebitAccountMethodSource(String value) {
      account.debit(new BigDecimal(value));
      assertNotNull(account.getBalance());
      assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
   }

   static List<String> amountList() {
      return Arrays.asList("100", "200", "500", "700", "1000", "1000.12344");
   }

   @Tag("param")
   @Nested
   class ParameterizedTests {

      // Example of a parameterized test
      @ParameterizedTest(name = "number {index} running with value {0} - {argumentsWithNames}")
      @ValueSource(doubles = {100, 200, 500, 700, 1000, 1000.12345, 1001})
      //@ValueSource(strings = { "100", "200", "500", "700", "1000", "1000.12345", "1001" })
      void testDebitAccountParameterized(Double amount) {
         account.debit(new BigDecimal(amount));
         assertNotNull(account.getBalance());
         assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
      }

      @ParameterizedTest(name = "number {index} running with value {0} - {argumentsWithNames}")
      @CsvSource({ "1,100", "2,200", "3,500", "4,700", "5,1000", "6,1000.12345" })
      void testDebitAccountParameterizedCsv(String index, String value) {
         System.out.println(index + " -> " + value);
         account.debit(new BigDecimal(value));
         assertNotNull(account.getBalance());
         assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
      }

      @ParameterizedTest(name = "number {index} running with value {0} - {argumentsWithNames}")
      @CsvFileSource(resources = "/data.csv")
         // The file should be added inside the resources folder
      void testDebitAccountParameterizedFileCsv(String value) {
         account.debit(new BigDecimal(value));
         assertNotNull(account.getBalance());
         assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
      }

      // Specifying balance and amount for the tests... GOOD!
      @ParameterizedTest(name = "number {index} running with value {0} - {argumentsWithNames}")
      @CsvSource({ "200,100,Arian,John", "250,200,Pepe,Pepe", "300,300,Maria,Maria", "510,500,John,John", "750,700,Lucas,Lucas",
            "1000.12345,1000.12344,Cata,Cata" })
      void testDebitAccountParameterizedCsv2(String balance, String amount, String expected, String actual) {
         System.out.printf("balance: %s, amount: %s, expectedName: %s, actualName: %s%n", balance, amount, expected, actual);
         account.setBalance(new BigDecimal(balance));
         account.setPerson(actual);

         account.debit(new BigDecimal(amount));

         assertNotNull(account.getBalance());
         assertNotNull(account.getPerson());

         assertEquals(expected, actual);
         assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
      }

   }

   @Tag("timeout")
   @Nested
   class ExamplesOfTimeOutTest {

      @Test
      @Timeout(1)
         // seconds (by default)
      void testTimeOut1() throws InterruptedException {
         // TimeUnit.SECONDS.sleep(2);
         TimeUnit.SECONDS.sleep(1);
      }

      @Test
      @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
      void testTimeOut2() throws InterruptedException {
         // TimeUnit.MILLISECONDS.sleep(1100);
         TimeUnit.MILLISECONDS.sleep(1000);
      }

      @Test
      void testTimeoutAssertions() {
         assertTimeout(Duration.ofSeconds(3), () -> {
            // Simulating heavy load or delay in method execution
            // TimeUnit.MILLISECONDS.sleep(3500);
            TimeUnit.MILLISECONDS.sleep(2000);
         });
      }
   }

}
