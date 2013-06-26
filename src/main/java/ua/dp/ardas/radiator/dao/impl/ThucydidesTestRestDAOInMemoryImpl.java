package ua.dp.ardas.radiator.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.ThucydidesTestRestDAO;
import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatistic;

@Component
@Scope("singleton")
public class ThucydidesTestRestDAOInMemoryImpl implements ThucydidesTestRestDAO {

	@Override
	public void insert(ThucydidesTestStatistic state) {
		
	}

	@Override
	public List<ThucydidesTestStatistic> findAll() {
		return null;
	}

}
