package ua.dp.ardas.radiator.jobs.report.builder;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.io.Closeables.closeQuietly;
import static java.lang.String.format;
import static ua.dp.ardas.radiator.jobs.buils.state.BuildState.States.SUCCESS;
import static ua.dp.ardas.radiator.jobs.buils.state.BuildStateInstances.UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.dao.ThucydidesTestStatisticDAO;
import ua.dp.ardas.radiator.jobs.buils.state.BuildState;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatistic;
import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatistic;

@Component
public class ReportBuilder {
	private static Logger LOG = Logger.getLogger(ReportBuilder.class.getName());
	
	@Autowired
	private BuildStateDAO buildStateDAO;
	@Autowired
	private ThucydidesTestStatisticDAO thucydidesTestStaisticDAO;
	@Autowired
	private SpiraTestStatisticDAO spiraTestStatisticDAO;
	@Value("${report.template.path}")
	private String pathToTemplate;
	@Value("${report.path}")
	private String pathToReport;

	
	public void build() {
		Map<String, String> parameters = agregateParameters();

		FileReader templateReader = newTemplateReader();
		FileWriter reportWriter = newReportWriter();
		
		Velocity.evaluate(new VelocityContext(parameters),reportWriter, "ReportFileGenerator", templateReader);
		
		closeQuietly(templateReader);
		closeQuietly(reportWriter);
	}

	private Map<String, String> agregateParameters() {
		Map<String, String> parameters = newHashMap();

		agrefateBuildStates(parameters);
		agrefateThucydidesTestStais(parameters);
		grefateSpiraTestStatistic(parameters);

		if (LOG.isInfoEnabled()) {
			LOG.info(format("Agregated params %s", parameters));
		}

		return parameters;
	}

	private void agrefateBuildStates(Map<String, String> parameters) {
		List<BuildState> buildStates = buildStateDAO.findLastData();
		
		if (null == buildStates) {
			buildStates = newArrayList(new BuildState(SUCCESS, UI));
		}
		
		for (BuildState state : buildStates) {
			parameters.put(format("buildState%sState", state.instances), String.valueOf(state.state));
			parameters.put(format("buildState%sErrorMessage", state.instances), String.valueOf(state.errorMessage));
			parameters.put(format("buildState%sFailedEmail", state.instances), String.valueOf(state.failedEmail));
			parameters.put(format("buildState%sFailedName", state.instances), String.valueOf(state.failedName));
		}
	}
	
	private void agrefateThucydidesTestStais(Map<String, String> parameters) {
		ThucydidesTestStatistic thucydidesTestStaistic = thucydidesTestStaisticDAO.findLastData();
		
		if (null == thucydidesTestStaistic) {
			thucydidesTestStaistic = new ThucydidesTestStatistic();
		}
		
		parameters.put("thucydidesTestPassed", String.valueOf(thucydidesTestStaistic.passed));
		parameters.put("thucydidesTestPending", String.valueOf(thucydidesTestStaistic.pending));
		parameters.put("thucydidesTestFailed", String.valueOf(thucydidesTestStaistic.failed));
		parameters.put("thucydidesTestErrors", String.valueOf(thucydidesTestStaistic.errors));
	}

	private void grefateSpiraTestStatistic(Map<String, String> parameters) {
		SpiraTestStatistic spiraTestStatistics = spiraTestStatisticDAO.findLastData();
		
		if (null == spiraTestStatistics) {
			spiraTestStatistics = new SpiraTestStatistic();
		}
		
		parameters.put("spiraTestHigh", String.valueOf(spiraTestStatistics.high));
		parameters.put("spiraTestMedium", String.valueOf(spiraTestStatistics.medium));
		parameters.put("spiraTestLow", String.valueOf(spiraTestStatistics.low));
		parameters.put("spiraTestChangeRequest", String.valueOf(spiraTestStatistics.changeRequest));

	}
	

	private FileWriter newReportWriter() {
		try {
			return new FileWriter(new File(pathToReport));
		} catch (IOException e) {
			LOG.error(format("Unable write report file: %s", pathToReport));
			throw new RuntimeException(e);
		}
	}

	private FileReader newTemplateReader() {
		try {
			return new FileReader(new File(pathToTemplate));
		} catch (FileNotFoundException e) {
			LOG.error(format("Unable find template file: %s", pathToTemplate));
			throw new RuntimeException(e);
		}
	}
}
