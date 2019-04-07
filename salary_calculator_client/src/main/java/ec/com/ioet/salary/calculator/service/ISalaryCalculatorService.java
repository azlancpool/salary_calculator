/**
 * 
 */
package ec.com.ioet.salary.calculator.service;

import java.io.File;

/**
 * @author cnaranjo
 *
 */
public interface ISalaryCalculatorService {

	/**
	 * Calculate the salary to be obtained by the employee.
	 * 
	 * @param file
	 *            File with information about the employee and the worked time.
	 * @throws Exception
	 *             In case there is an error while reading the information provided.
	 */
	void calculateSalary(File file) throws Exception;

}
