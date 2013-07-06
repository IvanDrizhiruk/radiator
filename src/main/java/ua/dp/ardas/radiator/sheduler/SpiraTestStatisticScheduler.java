package ua.dp.ardas.radiator.sheduler;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestStatisticController;
import ua.dp.ardas.radiator.utils.Timer;


@Component
public class SpiraTestStatisticScheduler {
	private static Logger LOG = Logger.getLogger(SpiraTestStatisticScheduler.class.getName());
	
	@Autowired
	private SpiraTestStatisticController spiraTestController;
	
	
	@Scheduled(cron="0 0,4,8,12,16,20 * * * *")
	private void  executeTask() {
		LOG.info(format("Start SpiraTestStatist calculation %s", currentLongTime()));
		Timer timer = new Timer();
		
		spiraTestController.execute();
		
		LOG.info(format("SpiraTestStatist calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
