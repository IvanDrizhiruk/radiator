package ua.dp.ardas.radiator.dao.impl;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;

@Component
public class SpiraTestStatisticDAOInMemory implements SpiraTestStatisticDAO {

	private List<SpiraTestStatistic> statistics = newArrayList();
	private Map<String, SpiraTestStatistic> statisticsByDay = Maps.newHashMap();
	
	
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
		return statisticsByDay.get(date);
	}

	public void addByDate(String day, SpiraTestStatistic statistic) {
		statisticsByDay.put(day, statistic);
	}
}