package ua.dp.ardas.radiator.dao;

import java.util.List;

import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;

public interface SpiraTestStatisticDAO {
	List<SpiraTestStatistic> findAll();

	void insert(SpiraTestStatistic statistic);

	SpiraTestStatistic findLastData();
	
	void addByDate(String day, SpiraTestStatistic statistic);
	
	SpiraTestStatistic findByDate(String data);
}
