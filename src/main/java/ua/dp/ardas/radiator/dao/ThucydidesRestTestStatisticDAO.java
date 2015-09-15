package ua.dp.ardas.radiator.dao;

import java.util.List;

import ua.dp.ardas.radiator.dto.thucydides.test.RestVersion;
import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;

public interface ThucydidesRestTestStatisticDAO {

	void insert(ThucydidesTestStatistic state, RestVersion version);

	List<ThucydidesTestStatistic> findAll(RestVersion version);

	ThucydidesTestStatistic findLastData(RestVersion version);
}