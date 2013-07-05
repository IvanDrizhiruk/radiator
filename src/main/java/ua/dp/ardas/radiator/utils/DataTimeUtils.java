package ua.dp.ardas.radiator.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataTimeUtils {

	private static final SimpleDateFormat longDataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String currentLongTime() {
		return longDataTimeFormat.format(Calendar.getInstance().getTime());
	}
}
