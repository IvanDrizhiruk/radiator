package ua.dp.ardas.radiator.dao.impl;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static ua.dp.ardas.radiator.utils.JsonUtils.toJSON;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;

import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;
import ua.dp.ardas.radiator.utils.FileUtils;
import ua.dp.ardas.radiator.utils.JsonUtils;

@Component
@Scope("singleton")
public class SpiraTestStatisticDAOInMemory implements SpiraTestStatisticDAO {
	private static Logger LOG = Logger
			.getLogger(SpiraTestStatisticDAOInMemory.class.getName());
	private static final String TEMP_FILE_PATH = "SpiraTestOnWeekStartStatistic.json";
	private List<SpiraTestStatistic> statistics = newArrayList();
	private Map<String, SpiraTestStatistic> statisticsByDay;
	
	
	@Override
	public void insert(SpiraTestStatistic statistic) {
		if (null == statistic) {
			return;
		}
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
		Type type = new TypeToken<Map<String, SpiraTestStatistic>>(){}.getType();
		
		String contentFromFile = FileUtils.loadFromFile(TEMP_FILE_PATH);
		
		return (isEmpty(contentFromFile)
				? Maps.<String, SpiraTestStatistic>newHashMap()
				: JsonUtils.<Map<String, SpiraTestStatistic>>fromJSON(contentFromFile, type));
	}
	
	private void saveToFile(Map<String, SpiraTestStatistic> statistics) {
		String statisticsString = toJSON(statistics);
		
		FileUtils.saveToFile(TEMP_FILE_PATH, statisticsString);
	}
}