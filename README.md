# Salary_calculator

This project is responsible for calculating the payment to be received by an employee, given an entry file with employee information, days and hours worked. For this, the following example format is used:

```bash
RENE=MO10:00-12:00,TU10:00-12:00,TH01:00-03:00,SA14:00-18:00,SU20:00-21:00
```

## Get Started

### Business Rules
The following business rules are defined in addition to those initially imposed for the correct operation of the program:
* The information will be read from the input.txt file contained in this repository, since the program has this file configured for reading information. This was defined because no graphical interface was requested in which the path of the file could have been specified.
* The file will contain information of a user and the hours worked per line. If this format is not maintained, the system will not be able to calculate the salary to be received optimally.

### Dependency manager
Gradle was used as dependency manager

## Dependencies

The system only works with two dependencies:
* **Lombok** Used for the management of logs as well as getters and setters methods. Information about lombok configuration in eclipse [here](https://projectlombok.org/setup/eclipse) 
* **Spring** Used for the management of instances generated within the application. This is because the system manages an architecture of n layers which helps its easy integration with another project.

**These do not intervene at all in the logic of the application business.**

## Run Project

1. Import gradle project in eclipse IDE (**STS** was used for this project).
2. Open de java file *SalaryCalculatorMain* this contains the main method to execute.

```java
/**
 * 
 */
package ec.com.ioet.salary.calculator.main;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ec.com.ioet.salary.calculator.config.AppConfig;
import ec.com.ioet.salary.calculator.service.ISalaryCalculatorService;

/**
 * Class that allows to execute the program.
 * 
 * @author cnaranjo
 *
 */
public class SalaryCalculatorMain {

	/**
	 * Main method
	 * 
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		ISalaryCalculatorService salaryCalculatorService = (ISalaryCalculatorService) ctx
				.getBean("salaryCalculatorService");
		salaryCalculatorService.calculateSalary(new File("..\\input.txt"));
	}

}

```
3. Execute this class from IDE

**You can also run the test located in the class [SalaryCalculatorTest](), the result will be the same.**

### Output example
**INPUT:**
```bash 
RENE=MO10:00-12:00,TU10:00-12:00,TH01:00-03:00,SA14:00-18:00,SU20:00-21:00
```
**OUTPUT:**
```bash 
[main] INFO ec.com.ioet.salary.calculator.business.impl.SalaryCalculatorBusiness - The amount to pay RENE  is: $215.0
```

## Content
The aplication contents four modules:
* **salary_calculator_root** Internally contains all modules associated with the project.
* **salary_calculator_client** Contains the interfaces and entities that can be exposed, this module does not contain anything related to the logic of the business.
* **salary_calculator_core** It contains all the business logic, as well as the configuration of bean's spring used in the project.
* **salary_calculator_test** It contains a class of test and also in this module the main class is exposed, from where it is possible to call to execute to the service that is in charge of the calculation of the salary.
