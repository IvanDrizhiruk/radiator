package ua.dp.ardas.radiator.jobs.report.builder;

import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;
import ua.dp.ardas.radiator.dao.ThucydidesTestStatisticDAO;

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

		String template = readReportTemplate(pathToTemplate);
		
		String report = applyParametersToTemplate(template, parameters);
		
		writeNewReportToPath(pathToReport, report);
		
		
	}

	private Map<String, String> agregateParameters() {
		Map<String, String> parameters = newHashMap();

		parameters.put("", value)

		LOG.info(lastToString(buildStateDAO.findLastData()));
		LOG.info(lastToString(thucydidesTestStaisticDAO.findAll()));
		LOG.info(lastToString(spiraTestStatisticDAO.findAll()));



		return parameters;
	}

	private String lastToString(List<?> list) {
		try {
			return Iterables.getLast(list).toString();
		} catch (Exception e) {
			return null;
		}
	}

	private String readReportTemplate(String pathToTemplate2) {
		return null;
	}
	
	private String applyParametersToTemplate(String template,
			Map<String, String> parameters) {
		return null;
	}

	private void writeNewReportToPath(String pathToReport2, String report) {
	}

}
