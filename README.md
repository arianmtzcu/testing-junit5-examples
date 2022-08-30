# Testing JUnit 5 Examples
This project demonstrates various examples of testing using JUnit 5. It includes multiple test classes that cover different testing scenarios such as conditional tests, parameterized tests, and basic unit tests for a simple account and bank model.

## Dependencies
The project uses the following main dependencies:
- **JUnit Jupiter:** `5.9.0` - For writing and running tests with JUnit 5.
- **Lombok:** `1.18.22` - For reducing boilerplate code in Java classes.
These dependencies are managed via Maven and specified in the `pom.xml` file.

## Test Classes
The project includes the following test classes:

### 1. `AccountTest.java`
This class contains tests for the `Account` class, focusing on the attributes and operations of a bank account. Tests include:
- **Balance Tests:** Ensures the correct balance after various operations.
- **Account Holder Name Tests:** Validates the correct setting and retrieval of the account holder's name.
- **Bank and Account Relationships:** Tests the relationships between a bank and its accounts, including checking account balances and associated banks.

### 2. `OtherAccountTest.java`
This class extends `AccountTest` and includes additional parameterized tests and timeout examples. Key features include:
- **Parameterized Tests:** Tests the debit operation on the account using different data sources such as CSV files and method sources.
- **Timeout Tests:** Verifies that certain operations complete within a specified time limit.

### 3. `ConditionalTests.java`
This class demonstrates conditional test execution based on environment variables, system properties, and operating systems. It includes:
- **Environment Variable Tests:** Runs tests only if specific environment variables are set.
- **System Property Tests:** Executes tests conditionally based on JVM system properties.
- **Operating System Tests:** Ensures tests are run only on specified operating systems (e.g., Windows, Linux).

## How to Run Tests
To run the tests in this project, you can use the following Maven command:
```bash
  mvn test
```

If you want to run only the tests with the account tag, uncomment the configuration in the pom.xml:
```xml
<configuration>
    <groups>account</groups>
</configuration>
```

## Contributions
This project is open to contributions. If you'd like to collaborate, please open an issue or send a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](https://opensource.org/license/MIT) file for more details.