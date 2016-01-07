package ua.dp.ardas.radiator.jobs.thucydides.test.result;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.dao.ThucydidesTestStatisticDAO;
import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;
import ua.dp.ardas.radiator.resr.client.ThucydidesTestRestClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.substringBetween;
import static ua.dp.ardas.radiator.utils.TypeUtils.toIntegerOrNull;

@Component
public class ThucydidesTestStatisticContorller {
	private static Logger LOG = Logger.getLogger(ThucydidesTestStatisticContorller.class.getName());
	
	@Autowired
	private ThucydidesTestRestClient restClient;
	@Autowired
	private ThucydidesTestStatisticDAO dao;
	@Value("${thucydides.test.status.url}")
	private String url;
			
	public void execute() {
        try {
//            String report = restClient.loadTestReport(url);
//            ThucydidesTestStatistic statistic = extractStatistic(report);
//            dao.insert(statistic);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	protected ThucydidesTestStatistic extractStatistic(String report) {
        if(null == report || report.isEmpty()) {
            return new ThucydidesTestStatistic();
        }
		Preconditions.checkNotNull(report);
		
		String reportArea = CharMatcher.anyOf("\r\n ").removeFrom(
				substringBetween(report,
				"<div class=\"test-count-summary\">",
				"<div id=\"test-results-tabs\""));
		
		Pattern pattern = Pattern.compile("(?i)(?s)class=\"test-count\">"
				+ "([0-9]+[,]?[0-9]*).*<.*>"
				+ ".*test-count\">([0-9]+[,]?[0-9]*).*<.*>"
				+ ".*test-count\">([0-9]+[,]?[0-9]*).*<.*>"
                + ".*test-count\">([0-9]+[,]?[0-9]*).*<.*>"
                + ".*test-count\">([0-9]+[,]?[0-9]*).*<.*>"
				+ ".*test-count\">([0-9]+[,]?[0-9]*).*<");
		Matcher matcher = pattern.matcher(reportArea);
		
		if (!matcher.find()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Unable extract thucydides statistic");
			}
			return null;
		}
		
		ThucydidesTestStatistic statistic = new ThucydidesTestStatistic();
		statistic.passed =  toIntegerOrNull(cleanStringNumber(matcher.group(1)));
		statistic.pending =  toIntegerOrNull(cleanStringNumber(matcher.group(2)));
		statistic.failed =  toIntegerOrNull(cleanStringNumber(matcher.group(3)));
		statistic.errors =  toIntegerOrNull(cleanStringNumber(matcher.group(4)));
		
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Thucydides statistic %s", statistic));
		}
		return statistic;
	}

	private String cleanStringNumber(String number) {
		return number.replaceAll("\\D*", EMPTY);
	}
}
