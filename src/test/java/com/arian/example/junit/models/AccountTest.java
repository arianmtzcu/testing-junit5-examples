package com.arian.example.junit.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

import com.arian.example.junit.exceptions.InsufficientFundsException;

// It's not advisable to use, as it creates a single reference per class in the lifecycle.
// Test classes should be stateless to avoid conditioning the results!
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountTest {

   protected Account account;

   protected TestInfo testInfo;

   protected TestReporter testReporter;

   // Always executed before each method
   @BeforeEach
   void initTestMethod(TestInfo testInfo, TestReporter testReporter) {
      System.out.println("Starting test method!");
      this.testInfo = testInfo;
      this.testReporter = testReporter;
      account = new Account("Arian", new BigDecimal("1000.12345"));

      String logMessage = String.format("DisplayName: %s TestMethod: %s Tags: %s", testInfo.getDisplayName(),
            testInfo.getTestMethod().get().getName(), testInfo.getTags());
      System.out.println("*** " + logMessage + " ***\n");
      // Publishing entry to standard output log (JUnit's own output as log),
      // JUnit Platform's own output
      testReporter.publishEntry(logMessage);
   }

   @AfterEach
   void tearDown() {
      System.out.println("Finishing test method!");
   }

   // Very useful when testing involves third-party services, and you don't want tests to fail because of third parties **Assumptions**
   @Test
   @DisplayName("Testing account balance if ENV=dev-1!")
   void testAccountBalanceIfDevEnvironment1() {
      boolean isDev = "dev".equals(System.getProperty("ENV"));
      assumeTrue(isDev); // semaphore condition!
      assertEquals(1000.12345, account.getBalance().doubleValue());
      assertNotNull(account.getBalance());
      assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0); // validating non-negative balance 1!
      assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0); // validating non-negative balance 2! (inverse)
   }

   // Very useful when testing involves third-party services, and you don't want tests to fail because of third parties **Assumptions**
   @Test
   @DisplayName("Testing account balance if ENV=dev-2!")
   void testAccountBalanceIfDevEnvironment2() {
      boolean isDev = "dev".equals(System.getProperty("ENV"));

      assumingThat(isDev, () -> {
         // Code that needs to be executed if the condition is met!
         System.out.println("Incoming assumingThat!");
         assertEquals(1000.12345, account.getBalance().doubleValue());
         assertNotNull(account.getBalance());
         assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0); // validating non-negative balance 1!
         assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0); // validating non-negative balance 2! (inverse)
      });
      System.out.println("End of the testAccountBalanceIfDevEnvironment2");
   }

   // Repeat the same test 3 times! very useful when working with randomness or non-deterministic behavior
   @DisplayName("Testing Debit Account with Repetition!")
   @RepeatedTest(value = 3, name = "{displayName} - Repetition {currentRepetition} of {totalRepetitions}")
   void testDebitOperationWithRepetition(RepetitionInfo info) {
      if (info.getCurrentRepetition() == 3) {
         System.out.println("Running repetition: " + info.getCurrentRepetition());
      }

      account.debit(new BigDecimal(100));
      assertNotNull(account.getBalance());
      assertEquals(900, account.getBalance().intValue());
      assertEquals("900.12345", account.getBalance().toPlainString());
   }

   @Test
   @Disabled
   @DisplayName("Fail Test Example with fail()")
   void testFailExample() {
      fail("This test is intentionally failed");
   }

   // Method belonging to the class, executed once before any instance is created,
   // generally used to initialize resources, open connections, etc.
   @BeforeAll
   static void setUpBeforeAll() {
      System.out.println("BeforeAll :: Initializing the test class!");
   }

   // Method belonging to the class, executed once after all tests have finished,
   // generally used to close connections, resources, etc.
   @AfterAll
   static void tearDownAfterAll() {
      System.out.println("AfterAll :: Finalizing the test class execution!");
   }

   @Tag("account")
   @Nested
   @DisplayName("Testing Account Class Attributes!")
   class AccountAttributesTest {

      // Hooks can be used: @BeforeEach, @AfterEach

      @Test
      @DisplayName("Testing Account Holder's Name - ORIGINAL")
      void testAccountHolderNameOriginal() {
         String expected = "ARIAN";
         // Message defined as a lambda expression, so it's only constructed in failure cases (performance!)
         // Messages are only created in cases where failures occur!
         assertNotNull(account.getPerson(), () -> "The account holder's name should not be null!");
         String actual = account.getPerson();
         assertEquals(expected, actual, () -> "The account holder's name is not as expected: " + expected + " but was: " + actual);
         assertNotEquals("Arian", actual, () -> "The expected value does not match the actual value");
      }

      @Test
      @DisplayName("Testing TestInfo & TestReporter")
      void testWithTestInfoAndReporter() {
         // Printing metadata of the currently executing method
         //System.out.println("DisplayName:" + testInfo.getDisplayName() + " TestMethod:" + testInfo.getTestMethod().get().getName() + " Tags:" +
         // testInfo.getTags());
         //System.out.println(testInfo.getTags());
         testReporter.publishEntry(testInfo.getTags().toString());
         if (testInfo.getTags().contains("account")) {
            testReporter.publishEntry("The 'account' tag is present, let's do something!");
         }

         String expected = "ARIAN";
         assertNotNull(account.getPerson(), () -> "The account holder's name should not be null!");
         String actual = account.getPerson();
         assertEquals(expected, actual, () -> "The account holder's name is not as expected: " + expected + " but was: " + actual);
         assertNotEquals("Arian", actual, () -> "The expected value does not match the actual value");
      }

      @Test
      @DisplayName("Testing Account Balance!")
      void testAccountBalance() {
         assertEquals(1000.12345, account.getBalance().doubleValue());
         assertNotNull(account.getBalance());
         assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0); // validating non-negative balance 1!
         assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0); // validating non-negative balance 2! (inverse)
      }

      // TDD -> Test Driven Development -> paradigm of development driven by unit tests
      @Test
      @DisplayName("Testing Equality of Two Accounts!")
      void testAccountEquality() {
         Account account1 = new Account("John Doe", new BigDecimal("8900.9997"));
         Account account2 = new Account("John Doe", new BigDecimal("8900.9997"));
         assertEquals(account2, account1, "The accounts should be considered equal");
      }

      @Tag("account")
      @Tag("bank")
      @Test
      @DisplayName("Testing Multiple Validations using assertAll...")
      void testBankAccountRelationships() {
         Account account1 = new Account("John Doe", new BigDecimal("2500"));
         Account account2 = new Account("Arian Doe", new BigDecimal("1500.8989"));

         Bank bank = new Bank();
         bank.setName("Banco del estado");
         bank.addAccount(account1);
         bank.addAccount(account2);

         assertAll("Bank and Account Relationships", () -> assertEquals("1500.8989", account2.getBalance().toPlainString()),
               () -> assertEquals(2, bank.getAccounts().size()), () -> assertEquals("Banco del estado", account1.getBank().getName()),
               () -> assertEquals("Arian Doe",
                     bank.getAccounts().stream().filter(c -> c.getPerson().equals("Arian Doe")).findFirst().get().getPerson()),
               () -> assertTrue(bank.getAccounts().stream().anyMatch(c -> c.getPerson().equals("John Doe"))));
      }
   }

   @Nested
   @DisplayName("Testing Account and Bank Operations!")
   class AccountOperationsTest {

      // Hooks can be used: @BeforeEach, @AfterEach

      @Tag("account")
      @Test
      @DisplayName("Testing Debit Operation!")
      void testDebitOperation() {
         account.debit(new BigDecimal(100));
         assertAll("Debit Operation", () -> assertNotNull(account.getBalance()), () -> assertEquals(900, account.getBalance().intValue()),
               () -> assertEquals("900.12345", account.getBalance().toPlainString()));
      }

      @Tag("account")
      @Test
      @DisplayName("Testing Credit Operation!")
      void testCreditOperation() {
         account.credit(new BigDecimal(100));
         assertAll("Credit Operation", () -> assertNotNull(account.getBalance()), () -> assertEquals(1100, account.getBalance().intValue()),
               () -> assertEquals("1100.12345", account.getBalance().toPlainString()));
      }

      @Tag("account")
      @Tag("bank")
      @Test
      @DisplayName("Testing Money Transfer Operation")
      void testMoneyTransferBetweenAccounts() {
         Account account1 = new Account("John Doe", new BigDecimal("2500"));
         Account account2 = new Account("Arian Doe", new BigDecimal("1500.8989"));
         Bank bank = new Bank();
         bank.setName("Banco del estado");
         bank.transfer(account1, account2, new BigDecimal(500));

         assertAll("Money Transfer", () -> assertEquals("2000", account1.getBalance().toPlainString()),
               () -> assertEquals("2000.8989", account2.getBalance().toPlainString()));
      }
   }

   @Tag("error")
   @Nested
   @DisplayName("Testing Controlled Errors!")
   class ExceptionHandlingTest {
      // Hooks can be used: @BeforeEach, @AfterEach

      @Tag("account")
      @Test
      @DisplayName("Testing Insufficient Funds Exception!")
      void testInsufficientFundsException() {
         Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            account.debit(new BigDecimal(1500));
         });

         assertEquals("Insufficient funds or money!", exception.getMessage());
      }
   }

}