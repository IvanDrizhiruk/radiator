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

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.substringBetween;
import static ua.dp.ardas.radiator.utils.TypeUtils.toIntegerOrNull;

@Component
public class ProtructorTestStatisticContorller {
	private static Logger LOG = Logger.getLogger(ProtructorTestStatisticContorller.class.getName());
	
	@Autowired
	private ThucydidesTestRestClient restClient;
	@Autowired
	private ThucydidesTestStatisticDAO dao;
	@Value("${thucydides.test.status.url}")
	private String url;
			
	public void execute() {
        try {
			String report = restClient.loadTestReport(url);
			ThucydidesTestStatistic statistic = extractStatistic(report);
            dao.insert(statistic);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	protected ThucydidesTestStatistic extractStatistic(String report) {
        if(null == report || report.isEmpty()) {
            return new ThucydidesTestStatistic();
        }
		Preconditions.checkNotNull(report);

		
		String reportArea = CharMatcher.anyOf("\r\n ").removeFrom(report);

		String totalStr  = substringBetween(reportArea, "<total>","</total>");
		String passedStr = substringBetween(reportArea, "<passed>","</passed>");
		String failedStr = substringBetween(reportArea, "<failed>","</failed>");

		Integer totalInt  = toIntegerOrNull(cleanStringNumber(totalStr));
		Integer passedInt = toIntegerOrNull(cleanStringNumber(passedStr));
		Integer failedInt = toIntegerOrNull(cleanStringNumber(failedStr));

		ThucydidesTestStatistic statistic = new ThucydidesTestStatistic();
		statistic.passed =  passedInt;
		statistic.pending = to0IfNull(totalInt) - (to0IfNull(passedInt) + to0IfNull(failedInt));
		statistic.failed =  failedInt;

		
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Protractor statistic %s", statistic));
		}
		return statistic;
	}

	private int to0IfNull(Integer number) {
		return null == number ? 0 : number;
	}

	private String cleanStringNumber(String number) {
		return number.replaceAll("\\D*", EMPTY);
	}
}
