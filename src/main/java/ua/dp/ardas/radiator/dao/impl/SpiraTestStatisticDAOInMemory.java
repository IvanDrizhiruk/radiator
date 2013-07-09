package ua.dp.ardas.radiator.dao.impl;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;
import com.google.gson.reflect.TypeToken;

import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;
import ua.dp.ardas.radiator.utils.JsonUtils;

@Component
public class SpiraTestStatisticDAOInMemory implements SpiraTestStatisticDAO {
	private static Logger LOG = Logger
			.getLogger(SpiraTestStatisticDAOInMemory.class.getName());
	private static final String TEMP_FILE_PATH = "SpiraTestOnWeekStartStatistic.json";
	private List<SpiraTestStatistic> statistics = newArrayList();
	private Map<String, SpiraTestStatistic> statisticsByDay;
	
	
	@Override
	public void insert(SpiraTestStatistic statistic) {
		statistics.clear();
		statistics.add(statistic);
	}

	@Override
	public List<SpiraTestStatistic> findAll() {
		return statistics;
	}

	@Override
	public SpiraTestStatistic findLastData() {
		try {
			return getLast(statistics);
		} catch (Exception e) {
			return null;
		}
	}

	public SpiraTestStatistic findByDate(String date) {
		if (null == statisticsByDay) {
			statisticsByDay = loadFromFile();
		}
			
		return statisticsByDay.get(date);
	}

	public void addByDate(String day, SpiraTestStatistic statistic) {
		if (null == statisticsByDay) {
			statisticsByDay = newHashMap();
		}
		statisticsByDay.put(day, statistic);
		saveToFile(statisticsByDay);
	}
	
	private Map<String, SpiraTestStatistic> loadFromFile() {
		InputSupplier<FileInputStream> newInputStreamSupplier = Files.newInputStreamSupplier(new File(TEMP_FILE_PATH));
		
		byte[] bytes = new byte[1000];
		try {
			ByteStreams.readFully(newInputStreamSupplier.getInput(), bytes);
		} catch (IOException e) {
			LOG.warn("Unable read tmp info", e);
		} finally {
			try {
				Closeables.closeQuietly(newInputStreamSupplier.getInput());
			} catch (IOException e) {
				LOG.warn("Unable read tmp info", e);
			}
		}
		
		Type type = new TypeToken<Map<String, SpiraTestStatistic>>(){}.getType();
		
		return JsonUtils.fromJSON(new String(bytes), type);
	}
	
	private void saveToFile(Map<String, SpiraTestStatistic> statistics) {
		OutputSupplier<FileOutputStream> newOutputStreamSupplier = Files.newOutputStreamSupplier(new File(TEMP_FILE_PATH));
		
		byte[] from = JsonUtils.toJSON(statistics).getBytes();
		try {
			ByteStreams.write(from, newOutputStreamSupplier);
		} catch (IOException e) {
			LOG.warn("Unable save tmp info", e);
		} finally {
			try {
				Closeables.closeQuietly(newOutputStreamSupplier.getOutput());
			} catch (IOException e) {
				LOG.warn("Unable save tmp info", e);
			}
		}
	}
}