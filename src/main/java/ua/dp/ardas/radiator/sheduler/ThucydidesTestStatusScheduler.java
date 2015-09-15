package ua.dp.ardas.radiator.sheduler;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.thucydides.rest.test.result.q.ThucydidesRestQTestStatisticContorller;
import ua.dp.ardas.radiator.jobs.thucydides.rest.test.result.r.ThucydidesRestRTestStatisticContorller;
import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatisticContorller;
import ua.dp.ardas.radiator.utils.Timer;


@Component
public class ThucydidesTestStatusScheduler {
	private static Logger LOG = Logger.getLogger(ThucydidesTestStatusScheduler.class.getName());
	
	@Autowired
	private ThucydidesTestStatisticContorller thucydidesTestStatusContorller;
	@Autowired
	private ThucydidesRestQTestStatisticContorller thucydidesResrQTestStatusContorller;
	@Autowired
	private ThucydidesRestRTestStatisticContorller thucydidesResrRTestStatusContorller;

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
		executeThucydidesRestQTestStatusContorller();
		executeThucydidesRestRTestStatusContorller();
	}

	private void executeThucydidesTestStatusContorller() {
		LOG.info(format("Start ThucydidesTestStatus calculation %s", currentLongTime()));
		Timer timer = new Timer();

		thucydidesTestStatusContorller.execute();

		LOG.info(format("ThucydidesTestStatus calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
	
	private void executeThucydidesRestQTestStatusContorller() {
		LOG.info(format("Start ThucydidesRestQTestStatus calculation %s", currentLongTime()));
		Timer timer = new Timer();

		thucydidesResrQTestStatusContorller.execute();

		LOG.info(format("ThucydidesRestQTestStatus calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
	
	private void executeThucydidesRestRTestStatusContorller() {
		LOG.info(format("Start ThucydidesRestRTestStatus calculation %s", currentLongTime()));
		Timer timer = new Timer();
		
		thucydidesResrRTestStatusContorller.execute();
		
		LOG.info(format("ThucydidesRestRTestStatus calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
