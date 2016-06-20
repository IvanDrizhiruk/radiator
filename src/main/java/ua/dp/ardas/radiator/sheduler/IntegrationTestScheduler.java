package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.jobs.integration.test.result.IntegrationTestResultContorller;
import ua.dp.ardas.radiator.utils.Timer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;


@Component
public class IntegrationTestScheduler {
	private static Logger LOG = Logger.getLogger(IntegrationTestScheduler.class.getName());
	
	@Inject
	private IntegrationTestResultContorller integrationTestResultContorller;

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
	
	@Scheduled(cron="${radiator.integrationTest.cron}")
	private void  executeTask() {

		LOG.info(format("Start ProtructorTestStatus calculation %s", currentLongTime()));
		Timer timer = new Timer();

		integrationTestResultContorller.execute();

		LOG.info(format("ProtructorTestStatus calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
