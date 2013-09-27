package ua.dp.ardas.radiator.sheduler;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.thucydides.rest.test.result.ThucydidesRestTestStatisticContorller;
import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatisticContorller;
import ua.dp.ardas.radiator.utils.Timer;


@Component
public class ThucydidesTestStatusScheduler {
	private static Logger LOG = Logger.getLogger(ThucydidesTestStatusScheduler.class.getName());
	
	@Autowired
	private ThucydidesTestStatisticContorller thucydidesTestStatusContorller;
	
	@Autowired
	private ThucydidesRestTestStatisticContorller thucydidesResrTestStatusContorller;

	@PostConstruct
	void initialize() {
		try {
			executeTask();
		} catch (Exception e) {
			LOG.warn("Unable init ThucydidesTestStatus.");
			if (LOG.isDebugEnabled()) {
				LOG.debug(String.format("Error: ", e));
			}
		}
	}
	
	@Scheduled(cron="${thucydides.test.status.cron}")
	private void  executeTask() {
		executeThucydidesTestStatusContorller();
		executeThucydidesRestTestStatusContorller();
	}

	private void executeThucydidesTestStatusContorller() {
		LOG.info(format("Start ThucydidesTestStatus calculation %s", currentLongTime()));
		Timer timer = new Timer();

		thucydidesTestStatusContorller.execute();

		LOG.info(format("ThucydidesTestStatus calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
	
	private void executeThucydidesRestTestStatusContorller() {
		LOG.info(format("Start ThucydidesRestTestStatus calculation %s", currentLongTime()));
		Timer timer = new Timer();

		thucydidesResrTestStatusContorller.execute();

		LOG.info(format("ThucydidesRestTestStatus calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
