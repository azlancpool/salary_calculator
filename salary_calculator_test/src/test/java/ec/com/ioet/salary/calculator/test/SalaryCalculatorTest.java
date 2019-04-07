package ec.com.ioet.salary.calculator.test;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ec.com.ioet.salary.calculator.config.AppConfig;
import ec.com.ioet.salary.calculator.service.ISalaryCalculatorService;

/**
 * 
 * @author cnaranjo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class SalaryCalculatorTest {

	@Autowired
	private ISalaryCalculatorService salaryCalculatorService;

	@Test
	public void test() throws Exception {
		salaryCalculatorService.calculateSalary(new File("..\\input.txt"));
	}
}
