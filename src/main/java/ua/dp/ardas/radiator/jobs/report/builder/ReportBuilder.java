package ua.dp.ardas.radiator.jobs.report.builder;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.calculateMondayDate;
import static ua.dp.ardas.radiator.utils.JsonUtils.toJSON;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.dao.ThucydidesTestStatisticDAO;
import ua.dp.ardas.radiator.dto.report.Report;
import ua.dp.ardas.radiator.jobs.buils.state.BuildState;

@Component
public class ReportBuilder {
	private static final String REPOST_IN_JSON = "repostInJson";

	private static Logger LOG = Logger.getLogger(ReportBuilder.class.getName());
	
	@Autowired
	private BuildStateDAO buildStateDAO;
	@Autowired
	private ThucydidesTestStatisticDAO thucydidesTestStaisticDAO;
	@Autowired
	private SpiraTestStatisticDAO spiraTestStatisticDAO;
	@Value("${spira.test.disabled:false}")
	private boolean isDisabledSpiraTest;


	private HashMap<String, String> reportToParameterMap(Report report) {
		HashMap<String, String> reportParameters = newHashMap();
		
		reportParameters.put(REPOST_IN_JSON, toJSON(report));
		return reportParameters;
	}

	public Report agregateReportObject() {
		Report report = new Report();

		agrefateBuildStates(report);
		agrefateThucydidesTestStais(report);
		agrefateSpiraTestStatistic(report);
		agrefateSpiraTestOnStartWeekStatistic(report);
		agrefateConfigurationSettings(report);

		if (LOG.isInfoEnabled()) {
			LOG.info(format("Agregated params %s", report));
		}

		return report;
	}

	private void agrefateConfigurationSettings(Report report) {
		report.configuration.put("spira.test.disabled", isDisabledSpiraTest);
	}

	private void agrefateBuildStates(Report report) {
		List<BuildState> buildStates = buildStateDAO.findLastData();

		for (BuildState state : buildStates) {
			report.buildStates.put(state.instances, state);
		}
	}

	private void agrefateThucydidesTestStais(Report report) {
		report.thucydidesTestStaistic = thucydidesTestStaisticDAO.findLastData();
		
	}

	private void agrefateSpiraTestStatistic(Report report) {
		if (isDisabledSpiraTest) {
			return;
		}
		report.spiraTestStatistics = spiraTestStatisticDAO.findLastData();
	}

	private void agrefateSpiraTestOnStartWeekStatistic(Report report) {
		if (isDisabledSpiraTest) {
			return;
		}
		report.spiraTestOnStartWeekStatistics = spiraTestStatisticDAO.findByDate(calculateMondayDate());
	}
}
