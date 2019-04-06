package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {
	public static String dataAtualFormatada() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return simpleDateFormat.format(dataAtual());
	}
	
	public static Date dataAtual() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}
}
