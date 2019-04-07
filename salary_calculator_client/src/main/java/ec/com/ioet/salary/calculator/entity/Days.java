package ec.com.ioet.salary.calculator.entity;

/**
 * Clase que permite identificar los dias de las semana segun las abreviaturas
 * ingresadas en el sistema.
 * 
 * @author cnaranjo
 *
 */
public enum Days {
	MONDAY("MO"), TUESDAY("TU"), WEDNESDAY("WE"), THURSDAY("TH"), FRIDAY("FR"), SATURDAY("SA"), SUNDAY("SU");

	private String day;

	private Days(String day) {
		this.day = day;
	}

	public String getDayValue() {
		return day;
	}

}
