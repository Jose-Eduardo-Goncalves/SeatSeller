package pt.portugueseexpress;
import java.time.LocalDate;
import java.util.logging.Logger;

public class PortugueseExpress {
	private int month;
	private int year;
	private int ccv;
	
	private static Logger LOGGER = Logger.getLogger(PortugueseExpress.class.getName());
	
	public void setNumber(String number) {
		// Fake
	}
	public void setCcv(int ccv) {
		this.ccv = ccv;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public boolean validate() {
		int cy = LocalDate.now().getYear();
		int cm = LocalDate.now().getMonth().getValue();
		LOGGER.info("Validating card...");
		return (year > cy || (year == cy && month >= cm));
	}
	
	public void block(double c) throws InvalidCardException {
		if (ccv % 2 == 1)
			throw new InvalidCardException();
		LOGGER.info("Blocking card...");
	}
	
	public void charge(double c) throws InvalidCardException {
		if (ccv % 2 == 1)
			throw new InvalidCardException();
		LOGGER.info("Charging card...");
	}
}
