package ua.dp.ardas.radiator.dao;

import java.util.List;

import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatistic;

public interface ThucydidesTestRestDAO {
	
	void insert(ThucydidesTestStatistic state);
	List<ThucydidesTestStatistic> findAll();

}
