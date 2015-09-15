package ua.dp.ardas.radiator.jobs.report.builder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.dao.ThucydidesRestTestStatisticDAO;
import ua.dp.ardas.radiator.dao.ThucydidesTestStatisticDAO;
import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.dto.report.Report;
import ua.dp.ardas.radiator.jobs.play.sound.SoundController;
import ua.dp.ardas.radiator.sheduler.MittingRemainder;

import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.String.format;
import static ua.dp.ardas.radiator.dto.thucydides.test.RestVersion.Q;
import static ua.dp.ardas.radiator.dto.thucydides.test.RestVersion.R;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.calculateMondayDate;
import static ua.dp.ardas.radiator.utils.JsonUtils.toJSON;

@Component
public class ReportBuilder {
	private static final String REPOST_IN_JSON = "repostInJson";

	private static Logger LOG = Logger.getLogger(ReportBuilder.class.getName());
	
	@Autowired
	private BuildStateDAO buildStateDAO;
	@Autowired
	private ThucydidesTestStatisticDAO thucydidesTestStaisticDAO;
	@Autowired
	private ThucydidesRestTestStatisticDAO thucydidesRestTestStatisticDAO;
	@Autowired
	private SpiraTestStatisticDAO spiraTestStatisticDAO;
	@Value("${spira.test.disabled:false}")
	private boolean isDisabledSpiraTest;
	@Autowired
	private MittingRemainder mittingRemainder;
	@Autowired
	private SoundController soundController;


	private HashMap<String, String> reportToParameterMap(Report report) {
		HashMap<String, String> reportParameters = newHashMap();
		
		reportParameters.put(REPOST_IN_JSON, toJSON(report));
		return reportParameters;
	}

	public Report agregateReportObject() {
		Report report = new Report();

		agregateBuildStates(report);
		agregateThucydidesTestStais(report);
		agregateThucydidesRestQTestStais(report);
		agregateThucydidesRestRTestStais(report);
		agregateSpiraTestStatistic(report);
		agregateSpiraTestOnStartWeekStatistic(report);
		agregateConfigurationSettings(report);

		if (LOG.isInfoEnabled()) {
			LOG.info(format("Agregated params %s", report));
		}

		return report;
	}

	private void agregateConfigurationSettings(Report report) {
		report.configuration.put("spira.test.disabled", isDisabledSpiraTest);
		report.configuration.put("play.sound.by.pathes", soundController.getSoundPathes());
	}

	private void agregateBuildStates(Report report) {
		List<BuildState> buildStates = buildStateDAO.findLastData();

		for (BuildState state : buildStates) {
			report.buildStates.put(state.instancesName, state);
		}
	}

	private void agregateThucydidesTestStais(Report report) {
		report.thucydidesTestStaistic = thucydidesTestStaisticDAO.findLastData();
		
	}
	
	private void agregateThucydidesRestQTestStais(Report report) {
		report.thucydidesRestQTestStaistic = thucydidesRestTestStatisticDAO.findLastData(Q);
	}
	
	
	private void agregateThucydidesRestRTestStais(Report report) {
		report.thucydidesRestRTestStaistic = thucydidesRestTestStatisticDAO.findLastData(R);
	}

	private void agregateSpiraTestStatistic(Report report) {
		if (isDisabledSpiraTest) {
			return;
		}
		report.spiraTestStatistics = spiraTestStatisticDAO.findLastData();
	}

	private void agregateSpiraTestOnStartWeekStatistic(Report report) {
		if (isDisabledSpiraTest) {
			return;
		}
		report.spiraTestOnStartWeekStatistics = spiraTestStatisticDAO.findByDate(calculateMondayDate());
	}
}
