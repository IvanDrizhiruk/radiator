package ua.dp.ardas.radiator.dao.impl;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;

public class SpiraTestStatisticDAOInMemory implements SpiraTestStatisticDAO {

	List<SpiraTestStatistic> statistics = newArrayList();

	
	@Override
	public void insert(SpiraTestStatistic statistic) {
		statistics.add(statistic);
	}

	@Override
	public List<SpiraTestStatistic> findAll() {
		return statistics;
	}

}