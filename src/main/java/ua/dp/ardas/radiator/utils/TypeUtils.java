package ua.dp.ardas.radiator.utils;

import org.apache.log4j.Logger;

public class TypeUtils {
	private static Logger LOG = Logger.getLogger(TypeUtils.class.getName());
	
	public static Integer toIntegerOrNull(String loadedString) {
		try {
			return Integer.valueOf(loadedString);
		}catch (NumberFormatException e) {
			LOG.error(String.format("Can not convert '%s' to integer.", loadedString), e);
			return null;
		}
	}
}
