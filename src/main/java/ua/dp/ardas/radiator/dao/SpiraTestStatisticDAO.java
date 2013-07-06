package ua.dp.ardas.radiator.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;

@Component
public class SpiraTestStatisticDAO {
	
	private List<SpiraTestStatistic> statistics = newArrayList();

	public void insert(SpiraTestStatistic statistic) {
		statistics.add(statistic);
	}

	public List<SpiraTestStatistic> findAll() {
		return statistics;
	}
}
