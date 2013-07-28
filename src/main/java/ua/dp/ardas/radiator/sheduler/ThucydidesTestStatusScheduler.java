package ua.dp.ardas.radiator.sheduler;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatisticContorller;
import ua.dp.ardas.radiator.utils.Timer;


@Component
public class ThucydidesTestStatusScheduler {
	private static Logger LOG = Logger.getLogger(ThucydidesTestStatusScheduler.class.getName());
	
	@Autowired
	private ThucydidesTestStatisticContorller thucydidesTestStatusContorller;

	@PostConstruct
	void initialize() {
		executeTask();
	}
	
	@Scheduled(fixedDelay=10000)
	private void  executeTask() {
		LOG.info(format("Start ThucydidesTestStatus calculation %s", currentLongTime()));
		Timer timer = new Timer();

		thucydidesTestStatusContorller.execute();

		LOG.info(format("ThucydidesTestStatus calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
