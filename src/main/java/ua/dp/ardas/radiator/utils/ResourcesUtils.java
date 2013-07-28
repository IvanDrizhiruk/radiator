package ua.dp.ardas.radiator.utils;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;

import java.io.IOException;

import com.google.common.io.Resources;

public class ResourcesUtils {

	public static String resourceAsString(String path) {
		try {
			return Resources.toString(getResource(path), UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
