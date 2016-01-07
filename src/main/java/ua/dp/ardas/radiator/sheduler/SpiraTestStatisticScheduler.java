package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatisticController;
import ua.dp.ardas.radiator.utils.Timer;

import javax.annotation.PostConstruct;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;


@Component
public class SpiraTestStatisticScheduler {
	private static Logger LOG = Logger.getLogger(SpiraTestStatisticScheduler.class.getName());
	
	@Autowired
	private SpiraTestStatisticController spiraTestController;
	
	@PostConstruct
	void initialize() {
		executeTask();
	}
	
	@Scheduled(cron="${spira.test.cron}")
	private void  executeTask() {
		LOG.info(format("Start SpiraTestStatist calculation %s", currentLongTime()));
		Timer timer = new Timer();
		
		spiraTestController.execute();
		
		LOG.info(format("SpiraTestStatist calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
