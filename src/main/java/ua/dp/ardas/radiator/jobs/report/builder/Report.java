package ua.dp.ardas.radiator.jobs.report.builder;

import static com.google.common.collect.Maps.newHashMap;

import java.util.HashMap;

import ua.dp.ardas.radiator.jobs.buils.state.BuildState;
import ua.dp.ardas.radiator.jobs.buils.state.BuildStateInstances;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;
import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatistic;
import ua.dp.ardas.radiator.utils.JsonUtils;

public class Report {

	public HashMap<BuildStateInstances, BuildState> buildStates = newHashMap();
	public ThucydidesTestStatistic thucydidesTestStaistic;
	public SpiraTestStatistic spiraTestStatistics;
	public SpiraTestStatistic spiraTestOnStartWeekStatistics;

	@Override
	public String toString() {
		return String.format("Report %s", JsonUtils.toJSON(this));
	}
}
