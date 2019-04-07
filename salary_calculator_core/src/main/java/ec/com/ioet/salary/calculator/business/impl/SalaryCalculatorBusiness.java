/**
 * 
 */
package ec.com.ioet.salary.calculator.business.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import ec.com.ioet.salary.calculator.business.ISalaryCalculatorBusiness;
import ec.com.ioet.salary.calculator.entity.Days;
import ec.com.ioet.salary.calculator.entity.Employee;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cnaranjo
 *
 */
@Slf4j
@Component("calulatorBusiness")
public class SalaryCalculatorBusiness implements ISalaryCalculatorBusiness {

	private static final double FIRST_RATE_PER_MINUTE_WEEKDAY = (double) 25 / 60;// 00:01 - 09:00
	private static final double SECOND_RATE_PER_MINUTE_WEEKDAY = (double) 15 / 60;// 09:01 - 18:00
	private static final double THIRD_RATE_PER_MINUTE_WEEKDAY = (double) 20 / 60;// 18:01 - 00:00

	private static final double FIRST_RATE_PER_MINUTE_WEEKEND = (double) 30 / 60;// 00:01 - 09:00
	private static final double SECOND_RATE_PER_MINUTE_WEEKEND = (double) 20 / 60;// 09:01 - 18:00
	private static final double THIRD_RATE_PER_MINUTE_WEEKEND = (double) 25 / 60;// 18:01 - 00:00

	@SuppressWarnings("unused")
	private static final LocalTime FIRST_PERIOD_START = LocalTime.parse("00:01");
	private static final LocalTime FIRST_PERIOD_END = LocalTime.parse("09:00");
	private static final LocalTime SECOND_PERIOD_START = LocalTime.parse("09:01");
	private static final LocalTime SECOND_PERIOD_END = LocalTime.parse("18:00");
	private static final LocalTime THIRD_PERIOD_START = LocalTime.parse("18:01");
	@SuppressWarnings("unused")
	private static final LocalTime THIRD_PERIOD_END = LocalTime.parse("00:00");

	private static final List<String> weekdays = Arrays
			.asList((String[]) new String[] { Days.MONDAY.getDayValue(), Days.TUESDAY.getDayValue(),
					Days.WEDNESDAY.getDayValue(), Days.THURSDAY.getDayValue(), Days.FRIDAY.getDayValue() });

	private static final List<String> weekend = Arrays
			.asList((String[]) new String[] { Days.SATURDAY.getDayValue(), Days.SUNDAY.getDayValue() });

	private Scanner sc;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void calculateSalary(File file) throws Exception {
		List<Employee> employeeList = new ArrayList<>();
		try {
			sc = new Scanner(file);

			// Se identifica el nombre del empleado y se calculan el salario a recibir
			int cont = 0;
			while (sc.hasNextLine()) {
				String lineReader = sc.nextLine();
				Employee employee = new Employee();
				String[] lineChain = lineReader.split("=");
				employee.setName(lineChain[0]);
				employee.setSalary(this.getEmployeeSalary(lineChain[1], cont++));
				employeeList.add(employee);
			}

			// Se imprime la salida con los salarios por empleado
			employeeList.stream().forEach(employee -> log.info("The amount to pay {}  is: ${}", employee.getName(),
					employee.getSalary().toString()));
		} catch (FileNotFoundException e) {
			log.error("File not found!", e);
			throw new Exception(e);
		}
	}

	/**
	 * Calculate the salary to be obtained by the employee.
	 * 
	 * @param lineReader
	 *            input with information about the employee and time worked.
	 * @return Total salary calculated.
	 * @throws Exception
	 *             In case there is an error while reading the information provided.
	 */
	private Double getEmployeeSalary(String lineReader, Integer cont) throws Exception {
		Double totalSalary = 0D;
		try {
			List<String> workedDays = Arrays.asList(lineReader.split(","));
			for (String workedDay : workedDays) {
				String day = workedDay.substring(0, 2);
				if (day.length() > 2) {
					log.error("Day information not found: {} in the line: {}", day, cont);
				} else {
					String time = workedDay.substring(2, workedDay.length());
					LocalTime timeBegin = LocalTime.parse(time.split("-")[0]);
					LocalTime timeEnd = LocalTime.parse(time.split("-")[1]);
					if (weekdays.contains(day)) {
						totalSalary = totalSalary + this.getTotalSalary(timeBegin, timeEnd, 0D, Boolean.TRUE);
					} else if (weekend.contains(day)) {
						totalSalary = totalSalary + this.getTotalSalary(timeBegin, timeEnd, 0D, Boolean.FALSE);
					} else {
						log.error("Information not found for day: {}", day);
					}
				}
			}
		} catch (Exception e) {
			log.error(
					"There was an error when calculating the value to pay, please check the information of the contents of the file {}",
					e);
			throw new Exception(e);
		}
		return totalSalary;
	}

	/**
	 * 
	 * @param timeBegin
	 *            Start time of work.
	 * @param timeEnd
	 *            End time of work.
	 * @param salary
	 *            Subtotal of the salary used uses the recursion of this method.
	 * @param isWeekday
	 *            TRUE if is weekday - FALSE: Other case.
	 * @return Total salary calculated.
	 */
	private double getTotalSalary(LocalTime timeBegin, LocalTime timeEnd, double salary, boolean isWeekday) {
		double subTotal;
		if (Duration.between(timeBegin, FIRST_PERIOD_END).getSeconds() >= 0) {
			if (Duration.between(FIRST_PERIOD_END, timeEnd).getSeconds() > 0) {
				subTotal = (Duration.between(timeBegin, FIRST_PERIOD_END).getSeconds() / 60D)
						* (isWeekday ? FIRST_RATE_PER_MINUTE_WEEKDAY : FIRST_RATE_PER_MINUTE_WEEKEND);
				return salary + getTotalSalary(SECOND_PERIOD_START, timeEnd, subTotal, isWeekday);
			} else {
				return salary + (Duration.between(timeBegin, timeEnd).getSeconds() / 60D
						* (isWeekday ? FIRST_RATE_PER_MINUTE_WEEKDAY : FIRST_RATE_PER_MINUTE_WEEKEND));
			}
		} else if (Duration.between(timeBegin, SECOND_PERIOD_END).getSeconds() >= 0) {
			if (Duration.between(SECOND_PERIOD_END, timeEnd).getSeconds() > 0) {
				subTotal = (Duration.between(timeBegin.minusMinutes(1L), SECOND_PERIOD_END).getSeconds() / 60D)
						* (isWeekday ? SECOND_RATE_PER_MINUTE_WEEKDAY : SECOND_RATE_PER_MINUTE_WEEKEND);
				return salary + getTotalSalary(THIRD_PERIOD_START, timeEnd, subTotal, isWeekday);
			} else {
				return salary + Duration.between(timeBegin, timeEnd).getSeconds() / 60D
						* (isWeekday ? SECOND_RATE_PER_MINUTE_WEEKDAY : SECOND_RATE_PER_MINUTE_WEEKEND);
			}
		} else {
			timeBegin = THIRD_PERIOD_START.equals(timeBegin) ? timeBegin.minusMinutes(1L) : timeBegin;
			return salary + (Duration.between(timeBegin, timeEnd).getSeconds() / 60D
					* (isWeekday ? THIRD_RATE_PER_MINUTE_WEEKDAY : THIRD_RATE_PER_MINUTE_WEEKEND));
		}
	}
}
