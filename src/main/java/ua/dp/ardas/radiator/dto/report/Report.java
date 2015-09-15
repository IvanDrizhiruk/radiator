package ua.dp.ardas.radiator.dto.report;

import static com.google.common.collect.Maps.newHashMap;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;
import ua.dp.ardas.radiator.utils.JsonUtils;

@XmlRootElement
public class Report {

	public HashMap<String, BuildState> buildStates = newHashMap();
	public ThucydidesTestStatistic thucydidesTestStaistic;
	public ThucydidesTestStatistic thucydidesRestQTestStaistic;
	public ThucydidesTestStatistic thucydidesRestRTestStaistic;
	public SpiraTestStatistic spiraTestStatistics;
	public SpiraTestStatistic spiraTestOnStartWeekStatistics;
	public Map<String, Object> configuration = newHashMap();

	@Override
	public String toString() {
		return String.format("Report %s", JsonUtils.toJSON(this));
	}
}
