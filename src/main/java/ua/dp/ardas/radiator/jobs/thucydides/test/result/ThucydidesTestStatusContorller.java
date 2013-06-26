package ua.dp.ardas.radiator.jobs.thucydides.test.result;

import static org.apache.commons.lang.StringUtils.substringBetween;
import static ua.dp.ardas.radiator.utils.TypeUtils.toIntegerOrNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import ua.dp.ardas.radiator.dao.ThucydidesTestRestDAO;
import ua.dp.ardas.radiator.resr.client.ThucydidesTestRestClient;

@Component
public class ThucydidesTestStatusContorller {
	private static Logger LOG = Logger.getLogger(ThucydidesTestStatusContorller.class.getName());
	
	@Autowired
	private ThucydidesTestRestClient restClient;
	@Autowired
	private ThucydidesTestRestDAO dao;
	
	@Value("thucydides.test.status.url")
	private String url;
			
	public void execute() {
		String report = restClient.loadTestReport(url);
		ThucydidesTestStatistic statistic = extractStatistic(report);
		dao.insert(statistic);
	}

	protected ThucydidesTestStatistic extractStatistic(String report) {
		Preconditions.checkNotNull(report);
		
		String reportArea = substringBetween(report,
				"<div class=\"test-count-summary\">",
				"<div id=\"test-results-tabs\">");
		
		
		Pattern pattern = Pattern.compile("([\\d]*)");
		Matcher matcher = pattern.matcher(reportArea);
		
		if (!matcher.find()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Unable extract thucydides statistic");
			}
			return null;
		}
		
		ThucydidesTestStatistic statistic = new ThucydidesTestStatistic();
		System.out.println(matcher.group(1));
		statistic.passed =  toIntegerOrNull(matcher.group(1));
		
		
//		statistic.pending =  toIntegerOrNull(matcher.group(2));
//		statistic.failed =  toIntegerOrNull(matcher.group(3));
//		statistic.errors =  toIntegerOrNull(matcher.group(4));
		
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("Thucydides statistic %s", statistic));
		}
		return statistic;
	}
}
