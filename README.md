<!-- README_PRESENTATION_START -->
<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=rect&height=140&color=0:111827,100:F97316&text=F.A.S.T.%20Framework&fontColor=FFFFFF&fontSize=30&fontAlignY=42&desc=Full-stack%20QA%20automation%20foundation%20for%20UI%20and%20API%20testing&descAlignY=68&descSize=15" alt="F.A.S.T. Framework banner" />
</p>

<p align="center">
  <img alt="Java: Automation" src="https://img.shields.io/badge/Java-Automation-F97316?style=for-the-badge" /> <img alt="Selenium: UI Testing" src="https://img.shields.io/badge/Selenium-UI%20Testing-43B02A?style=for-the-badge" /> <img alt="TestNG: Runner" src="https://img.shields.io/badge/TestNG-Runner-0B5CAD?style=for-the-badge" /> <img alt="Allure: Reporting" src="https://img.shields.io/badge/Allure-Reporting-FF6A00?style=for-the-badge" /> <img alt="Scope: UI + API" src="https://img.shields.io/badge/Scope-UI%20%2B%20API-111827?style=for-the-badge" />
</p>

<table>
  <tr><td><strong>Role signal</strong></td><td>Java QA Automation framework ownership</td></tr>
<tr><td><strong>What to inspect</strong></td><td><code>BrowserManager</code>, base test layer, helpers, Allure setup</td></tr>
<tr><td><strong>Best for</strong></td><td>QA Automation, SDET, Java test framework roles</td></tr>
</table>

<!-- README_PRESENTATION_END -->

# F.A.S.T. Framework

Full Stack Automation Software Tester framework for UI and API-oriented test automation.

F.A.S.T. is a Java automation framework designed to organize reusable test infrastructure, browser execution, reporting, configuration, and future API test layers in a clean structure. The current public branch strongly demonstrates the UI automation layer with Selenium, TestNG, Allure, configuration helpers, browser management, logging, and reusable interaction helpers. The framework is positioned as a foundation for both UI and API suites, with API tests intended to live as a parallel layer beside UI tests.

## What This Project Demonstrates

For recruiters and QA engineering reviewers, this repository demonstrates:

- Java-based automation framework design;
- Selenium WebDriver test execution;
- TestNG suite organization;
- Allure reporting integration;
- reusable base test classes;
- browser setup and execution abstraction;
- configuration management through properties files;
- helper classes for common UI interactions;
- logging and thread-aware test execution support;
- a framework shape that can be extended with API tests, additional environments, and CI execution.

## Scope

Current public implementation:

- Selenium WebDriver UI tests;
- TestNG runner configuration;
- Allure reporting;
- browser manager utility;
- application helper layer;
- element helper layer;
- property file reader utilities;
- logging configuration;
- DemoQA test examples.

Framework intent:

- UI test layer for browser workflows;
- API test layer for endpoint validation;
- reusable shared configuration;
- common reporting and execution conventions;
- CI-friendly Maven execution.

Note: the public tree currently shows the UI layer most clearly. The README describes the intended full-stack automation direction while keeping the implementation notes tied to the visible code.

## Technology Stack

| Area | Tools |
|---|---|
| Language | Java |
| Build | Maven |
| UI automation | Selenium WebDriver |
| Test runner | TestNG |
| Reporting | Allure TestNG, Allure Maven |
| Logging | Log4j, SLF4J |
| Utilities | Lombok, Reflections |
| Configuration | `.properties` files |

## Repository Structure

```text
src/test/java/
  base/
    TestBase.java
    TestWithHelpers.java

  helpers/
    AppHelper.java
    SeleniumElementsHelper.java

  tests/UI/
    DemoQA.java

  utils/
    BrowserManager.java
    PropertyFile.java
    PropertyHelper.java
    ThreadLogger.java

src/test/resources/
  testng.xml
  testng-selenium.properties
  allure.properties
  log4j2.xml
  drivers/
```

## Key Implementation Ideas

### Base test layer

`TestBase` and `TestWithHelpers` centralize setup and shared behavior so individual tests can focus on intent instead of boilerplate.

### Browser abstraction

`BrowserManager` is the place where browser creation and execution strategy are managed. That gives the framework a single extension point for local browser execution, Selenium Grid, or remote providers.

### Helper layer

`SeleniumElementsHelper` and `AppHelper` reduce repeated WebDriver calls and provide a more readable test layer.

### Configuration layer

`PropertyFile`, `PropertyHelper`, and `IPropertyGet` support property-based configuration, which is useful when separating local, CI, staging, and production-like test settings.

### Reporting

Allure is configured as the reporting layer, allowing test results to be converted into a recruiter- and team-friendly execution report.

## Running Locally

Requirements:

- Java compatible with the Maven configuration;
- Maven;
- Chrome / browser driver setup appropriate for your OS;
- Allure command line if you want to serve reports locally.

Install dependencies:

```bash
mvn clean install
```

Run tests:

```bash
mvn test
```

Generate and serve Allure report:

```bash
mvn clean test allure:serve
```

## Suggested API Layer Direction

The framework is intended to support API tests as a first-class layer. A clean extension would add:

```text
src/test/java/tests/API/
src/test/java/api/
src/test/java/models/
src/test/resources/schemas/
```

Typical responsibilities:

- request builders;
- reusable API clients;
- response assertions;
- schema validation;
- test data setup;
- Allure REST attachments;
- environment-specific base URLs and credentials through properties.

## What To Review First

1. `pom.xml` for framework dependencies and reporting setup.
2. `BrowserManager.java` for execution strategy.
3. `TestBase.java` and `TestWithHelpers.java` for framework structure.
4. `SeleniumElementsHelper.java` for reusable WebDriver interaction patterns.
5. `DemoQA.java` for example UI tests.

## Recruiter Signal

This project is useful as a QA Automation portfolio item because it shows how I think about:

- framework structure;
- reusable test architecture;
- UI automation maintainability;
- reporting and diagnostics;
- configuration-driven execution;
- preparing a codebase for broader UI and API test coverage.

It is a good match for roles requiring Java, Selenium, TestNG, Allure, Maven, CI-oriented automation, and ownership of test framework design.
