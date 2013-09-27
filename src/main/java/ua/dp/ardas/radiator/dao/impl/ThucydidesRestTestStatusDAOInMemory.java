package ua.dp.ardas.radiator.dao.impl;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.ThucydidesRestTestStatisticDAO;
import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;

@Component
@Scope("singleton")
public class ThucydidesRestTestStatusDAOInMemory implements ThucydidesRestTestStatisticDAO {

	private List<ThucydidesTestStatistic> thucydidesTestStatistic = newArrayList();
	
	@Override
	public void insert(ThucydidesTestStatistic state) {
		if (null == state) {
			return;
		}
		
		thucydidesTestStatistic.clear();
		thucydidesTestStatistic.add(state);
	}

	@Override
	public List<ThucydidesTestStatistic> findAll() {
		return thucydidesTestStatistic;
	}

	@Override
	public ThucydidesTestStatistic findLastData() {
		try {
			return getLast(thucydidesTestStatistic);
		}catch (Exception e) {
			return null;
		}
	}
}