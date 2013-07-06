package ua.dp.ardas.radiator.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;

@Component
public interface SpiraTestStatisticDAO {
	List<SpiraTestStatistic> findAll();
	void insert(SpiraTestStatistic statistic);
}
