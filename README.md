
# Comprehensive Automation Testing Framework
#### (F.A.S.T. - Full Stack Automation Software Tester)

## Overview
This repository houses a robust automation testing framework designed to facilitate front-end and API testing. Utilizing a powerful stack of Java, Selenium, TestNG, Allure, and RestAssured, this framework aims to demonstrate best practices in software testing while showcasing the RothFick proficiency in automation.

## Features
- **Multi-browser Support**: Utilizes Selenium for cross-browser testing, ensuring compatibility across major browsers.
- **API Testing**: Leverages RestAssured to handle API testing, allowing for comprehensive endpoint validation.
- **Modular Design**: Implements a clean separation of concerns with distinct modules for different testing types.
- **Automated Reporting**: Integrates Allure for generating insightful test reports, enhancing the visibility of test results.
- **Continuous Integration**: Configured to work with CI/CD pipelines, facilitating automated builds and tests execution.

## Usage
To utilize this framework, follow the steps outlined below:

### Prerequisites
Ensure you have the following installed:
- Java JDK 8 or higher
- Maven
- All necessary browser drivers (e.g., ChromeDriver for Google Chrome)

### Setting Up
1. **Clone the Repository**
   ```bash
   git clone https://github.com/<your-username>/<repository-name>.git
   cd <repository-name>
   ```

2. **Install Dependencies**
   ```bash
   mvn install
   ```

3. **Run Tests**
   Execute tests using Maven:
   ```bash
   mvn test
   ```

### Configuration
Modify the `src/main/resources/config.properties` file to update base URLs, credentials, or other necessary parameters.

### Adding Tests
1. **Frontend Tests**: Place your Selenium tests under `src/test/java/frontend`.
2. **Backend Tests**: Use TestNG for backend-specific tests, located under `src/test/java/backend`.
3. **API Tests**: RestAssured tests should be placed under `src/test/java/api`.

## Test Management
- **Page Objects**: Utilize the Page Object Model (POM) for maintaining web interactions.
- **Data Management**: Employ data-driven testing using external data sources like Excel or CSV files for scalable test cases.

## Reporting
For purposes of generating report you should use command below
```bash
mvn clean test allure:serve
```
This command will open up the Allure report dashboard in your default web browser, showing detailed test execution results.

## Contribution
Contributions to this framework are welcome! Please adhere to the provided coding standards and submit pull requests for any enhancements.

## License
Distributed under the MIT License. See `LICENSE` for more information.
