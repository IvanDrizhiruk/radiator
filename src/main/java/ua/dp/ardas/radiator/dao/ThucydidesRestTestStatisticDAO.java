package ua.dp.ardas.radiator.dao;

import java.util.List;

import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;

public interface ThucydidesRestTestStatisticDAO {

	void insert(ThucydidesTestStatistic state);

	List<ThucydidesTestStatistic> findAll();

	ThucydidesTestStatistic findLastData();
}