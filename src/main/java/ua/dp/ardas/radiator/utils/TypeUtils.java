package ua.dp.ardas.radiator.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class TypeUtils {
	private static Logger LOG = Logger.getLogger(TypeUtils.class.getName());
	
	public static Integer toIntegerOrNull(String loadedString) {
		if(StringUtils.isEmpty(loadedString)) {
			return null;
		}

		try {
			return Integer.valueOf(loadedString);
		}catch (NumberFormatException e) {
			LOG.error(String.format("Can not convert '%s' to integer.", loadedString), e);
			return null;
		}
	}

	public static Long toLongOrNull(String loadedString) {
		if(StringUtils.isEmpty(loadedString)) {
			return null;
		}

		try {
			return Long.valueOf(loadedString);
		}catch (NumberFormatException e) {
			LOG.error(String.format("Can not convert '%s' to long.", loadedString), e);
			return null;
		}
	}
}
