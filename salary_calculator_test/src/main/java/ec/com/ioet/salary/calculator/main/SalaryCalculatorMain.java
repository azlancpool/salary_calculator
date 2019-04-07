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
