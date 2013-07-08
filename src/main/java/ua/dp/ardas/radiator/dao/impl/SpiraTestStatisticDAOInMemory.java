package ua.dp.ardas.radiator.dao.impl;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;

@Component
public class SpiraTestStatisticDAOInMemory implements SpiraTestStatisticDAO {

	List<SpiraTestStatistic> statistics = newArrayList();

	
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
}