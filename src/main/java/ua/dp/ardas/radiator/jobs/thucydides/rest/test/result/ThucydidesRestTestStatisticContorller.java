package ua.dp.ardas.radiator.jobs.thucydides.rest.test.result;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.dao.ThucydidesRestTestStatisticDAO;
import ua.dp.ardas.radiator.dto.thucydides.test.RestVersion;
import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;
import ua.dp.ardas.radiator.resr.client.ThucydidesTestRestClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.substringBetween;
import static ua.dp.ardas.radiator.utils.TypeUtils.toIntegerOrNull;

@Component
public class ThucydidesRestTestStatisticContorller {
	private static Logger LOG = Logger.getLogger(ThucydidesRestTestStatisticContorller.class.getName());
	
	@Autowired
	private ThucydidesTestRestClient restClient;
	@Autowired
	private ThucydidesRestTestStatisticDAO dao;
	@Value("${thucydides.rest.test.status.url}")
	private String url;
			
	public void execute() {
		String report = restClient.loadTestReport(url);
		ThucydidesTestStatistic statistic = extractStatistic(report);
		dao.insert(statistic, RestVersion.version);
	}

	protected ThucydidesTestStatistic extractStatistic(String report) {
		Preconditions.checkNotNull(report);
		
		String reportArea = CharMatcher.anyOf("\r\n ").removeFrom(
				substringBetween(report,
					"<span class=\"test-count-title\">",
					"<div id=\"pie_chart\""));
		
		Pattern pattern = Pattern.compile("(?i)(?s)class=\"test-count\">([0-9]+).*?<.*?>([0-9]+).*?<.*?>([0-9]+).*");
		Matcher matcher = pattern.matcher(reportArea);
		
		if (!matcher.find()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Unable extract thucydides statistic");
			}
			return null;
		}
		
		ThucydidesTestStatistic statistic = new ThucydidesTestStatistic();
		statistic.passed =  toIntegerOrNull(matcher.group(1));
		statistic.pending =  toIntegerOrNull(matcher.group(2));
		statistic.failed =  toIntegerOrNull(matcher.group(3));
		
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Thucydides statistic %s", statistic));
		}
		return statistic;
	}
}
