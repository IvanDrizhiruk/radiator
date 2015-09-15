package ua.dp.ardas.radiator.dao.impl;

import static com.google.common.collect.Iterables.getLast;
import static ua.dp.ardas.radiator.dto.thucydides.test.RestVersion.Q;
import static ua.dp.ardas.radiator.dto.thucydides.test.RestVersion.R;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import ua.dp.ardas.radiator.dao.ThucydidesRestTestStatisticDAO;
import ua.dp.ardas.radiator.dto.thucydides.test.RestVersion;
import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;

@Component
@Scope("singleton")
public class ThucydidesRestTestStatusDAOInMemory implements ThucydidesRestTestStatisticDAO {

	private Map<RestVersion, List<ThucydidesTestStatistic>> thucydidesTestStatistic
			= ImmutableMap.<RestVersion, List<ThucydidesTestStatistic>>builder()
			.put(Q, Lists.<ThucydidesTestStatistic>newArrayList())
			.put(R, Lists.<ThucydidesTestStatistic>newArrayList())
			.build();
	
	
	@Override
	public void insert(ThucydidesTestStatistic state, RestVersion version) {
		if (null == state) {
			return;
		}
		
		getStorageByVersion(version).clear();
		getStorageByVersion(version).add(state);
	}

	private List<ThucydidesTestStatistic> getStorageByVersion(RestVersion version) {
		return thucydidesTestStatistic.get(version);
	}

	@Override
	public List<ThucydidesTestStatistic> findAll(RestVersion version) {
		return getStorageByVersion(version);
	}

	@Override
	public ThucydidesTestStatistic findLastData(RestVersion version) {
		try {
			return getLast(getStorageByVersion(version));
		}catch (Exception e) {
			return null;
		}
	}
}