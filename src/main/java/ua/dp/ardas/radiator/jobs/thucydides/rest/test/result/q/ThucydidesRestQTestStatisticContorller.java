package ua.dp.ardas.radiator.jobs.thucydides.rest.test.result.q;

import static org.apache.commons.lang.StringUtils.substringBetween;
import static ua.dp.ardas.radiator.dto.thucydides.test.RestVersion.Q;
import static ua.dp.ardas.radiator.utils.TypeUtils.toIntegerOrNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;

import ua.dp.ardas.radiator.dao.ThucydidesRestTestStatisticDAO;
import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;
import ua.dp.ardas.radiator.resr.client.ThucydidesTestRestClient;

@Component
public class ThucydidesRestQTestStatisticContorller {
	private static Logger LOG = Logger.getLogger(ThucydidesRestQTestStatisticContorller.class.getName());
	
	@Autowired
	private ThucydidesTestRestClient restClient;
	@Autowired
	private ThucydidesRestTestStatisticDAO dao;
	@Value("${thucydides.rest.q.test.status.url}")
	private String url;
			
	public void execute() {
//        try {
//            String report = restClient.loadTestReport(url);
//            ThucydidesTestStatistic statistic = extractStatistic(report);
//            dao.insert(statistic, Q);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
	}

	protected ThucydidesTestStatistic extractStatistic(String report) {
        if(null == report || report.isEmpty()) {
            return new ThucydidesTestStatistic();
        }

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
