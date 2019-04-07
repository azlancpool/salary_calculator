/**
 * 
 */
package ec.com.ioet.salary.calculator.business;

import java.io.File;

/**
 * @author cnaranjo
 *
 */
public interface ISalaryCalculatorBusiness {

	/**
	 * Metodo que se encarga del calculo del salario dado un archivo con informacion
	 * relacionada a las personas y sus horas trabajadas.
	 * 
	 * @param file
	 *            Archivo de donde se obtendra la informacion de las horas
	 *            trabajadas por persona
	 * @throws Exception
	 *             Execpcion captuarada en caso de que se produzca un error en el
	 *             proceso del calculo de salarios.
	 */
	void calculateSalary(File file) throws Exception;

}
