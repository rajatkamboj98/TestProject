package base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class BaseClass {

	public static String toDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return df.format(cal.getTime());
		//System.out.println(df.format(cal.getTime()));
	}

	public static String subOneDay(String date) {
		return LocalDate.parse(date).minusDays(1).toString();
	}
}
