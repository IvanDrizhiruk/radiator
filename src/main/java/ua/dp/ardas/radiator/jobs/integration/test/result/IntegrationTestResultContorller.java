package ua.dp.ardas.radiator.jobs.integration.test.result;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.domain.IntegrationTestResult;
import ua.dp.ardas.radiator.repository.IntegrationTestResultRepository;
import ua.dp.ardas.radiator.restclient.IntegrationTestRestClient;
import ua.dp.ardas.radiator.utils.DataTimeUtils;
import ua.dp.ardas.radiator.utils.TypeUtils;

import javax.inject.Inject;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.EMPTY;

@Component
public class IntegrationTestResultContorller {
	private static Logger LOG = Logger.getLogger(IntegrationTestResultContorller.class.getName());
	
	@Inject
	private IntegrationTestRestClient restClient;
	@Inject
	private IntegrationTestResultRepository repository;
	@Value("${radiator.integrationTest.url}")
	private String url;
	private boolean equals;

	public void execute() {
        try {

			String report = restClient.loadTestReport(url);
			IntegrationTestResult statistic = extractStatistic(report);

			saveResultIfNeed(statistic);
        } catch (Exception e) {
			LOG.warn("Some problem on processing of integration test result");
        }
	}

	private void saveResultIfNeed(IntegrationTestResult statistic) {
		Optional<IntegrationTestResult> lastResult = repository.findLast();

		if (lastResult.isPresent()) {
			IntegrationTestResult lastResultObj = lastResult.get();

			if (!isEquals(lastResultObj.getTotal(), statistic.getTotal())
					|| !isEquals(lastResultObj.getPassed(), statistic.getPassed())
					|| !isEquals(lastResultObj.getPending(), statistic.getPending())
					|| !isEquals(lastResultObj.getFailed(), statistic.getFailed()) ) {
				repository.save(statistic);
			}


		} else {
			repository.save(statistic);
		}


	}

	protected IntegrationTestResult extractStatistic(String report) {
        if(null == report || report.isEmpty()) {
            return new IntegrationTestResult();
        }
		Preconditions.checkNotNull(report);


		String reportArea = CharMatcher.anyOf("\r\n ").removeFrom(report);

		String totalStr  = StringUtils.substringBetween(reportArea, "<total>", "</total>");
		String passedStr = StringUtils.substringBetween(reportArea, "<passed>", "</passed>");
		String failedStr = StringUtils.substringBetween(reportArea, "<failed>", "</failed>");

		Long totalLong  = TypeUtils.toLongOrNull(cleanStringNumber(totalStr));
		Long passedLong = TypeUtils.toLongOrNull(cleanStringNumber(passedStr));
		Long failedLong = TypeUtils.toLongOrNull(cleanStringNumber(failedStr));

		IntegrationTestResult statistic = new IntegrationTestResult();
		statistic.setPassed(passedLong);
		statistic.setPending(to0IfNull(totalLong) - (to0IfNull(passedLong) + to0IfNull(failedLong)));
		statistic.setFailed(failedLong);
		statistic.setTotal(totalLong);
		statistic.setExtractingDate(DataTimeUtils.nowZonedDateTime());


		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Protractor statistic %s", statistic));
		}
		return statistic;
	}

	private long to0IfNull(Long number) {
		return null == number ? 0 : number;
	}

	private String cleanStringNumber(String number) {
		return number.replaceAll("\\D*", EMPTY);
	}

	public boolean isEquals(Long l1, Long l2) {
		if (null == l1 || null == l2) {
			return l1 == l2;
		}

		return l1.equals(l2);
	}
}
