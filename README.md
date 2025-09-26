# Oktay Uyar Test Automation Case

Java maven project for test automation, UI acceptance, API acceptance testing.
Created with lessons learned from a large number of development projects to provide all commonly required components and concepts.

## Concepts Included


* Page Object pattern
* Common web page interaction methods
* Common api interaction methods
* Externalised test configuration
* Commonly used test utility classes

## Tools

* Maven
* TestNG
* Selenium Webdriver
* Log4j
* REST Assured


## Usage

The project is broken into separate modules for API and UI testing. Each of these modules can be utilised independently of the others using maven profiles.

To run all modules, navigate to `Oktay_Uyar_Case` directory and run:

`mvn clean test`


## Reporting

To create a report  run:

`mvn allure:report`

The report will be create in root directory under `/target/allure-report`.

For open the report:

[Allure Report](http://localhost:63342/Oktay_Uyar_Case/target/allure-report/index.html)

