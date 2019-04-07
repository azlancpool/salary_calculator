/**
 * 
 */
package ec.com.ioet.salary.calculator.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ec.com.ioet.salary.calculator.business.ISalaryCalculatorBusiness;
import ec.com.ioet.salary.calculator.service.ISalaryCalculatorService;

/**
 * @author cnaranjo
 *
 */
@Service("salaryCalculatorService")
public class SalaryCalculatorService implements ISalaryCalculatorService {

	@Autowired
	@Lazy
	private ISalaryCalculatorBusiness calulatorBusiness;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void calculateSalary(File file) throws Exception{
		calulatorBusiness.calculateSalary(file);
	}

}
