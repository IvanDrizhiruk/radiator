package ua.dp.ardas.radiator.utils;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Files.readLines;
import static java.nio.charset.Charset.forName;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

public class FileUtils {
	private static Logger LOG = Logger.getLogger(FileUtils.class.getName());

	public static String loadFromFile(String filePath) {
		File file = new File(filePath);
		try {
			List<String> lines = readLines(file, UTF_8);

			return Joiner.on("\n").join(lines);
		} catch (IOException e) {
			LOG.error(String.format("Unable read file by path: %s", filePath), e);
		} catch (Exception e) {
			LOG.error(String.format("Error on read processing file with path: %s", filePath), e);
		}

		return null;
	}

	public static void saveToFile(String filePath, String content) {
		File file = new File(filePath);
		try {
			Files.write(content.getBytes(forName(UTF_8.name())), file);
		} catch (IOException e) {
			LOG.error(String.format("Unable write file by path: %s", filePath), e);
		} catch (Exception e) {
			LOG.error(String.format("Error on write processing file with path: %s", filePath), e);
		}
	}

}
