package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestOnStartWeekStatisticController;
import ua.dp.ardas.radiator.utils.Timer;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;

@Component
public class SpiraTestOnStartWeekStatisticScheduler {
	private static Logger LOG = Logger.getLogger(SpiraTestOnStartWeekStatisticScheduler.class.getName());
	
	@Autowired
	private SpiraTestOnStartWeekStatisticController spiraTestOnStartWeekStatisticController;
	
	
	//@Scheduled(cron="${spira.test.on.start.week.statistic.cron}")
	public void  executeTask() {
		LOG.info(format("Start SpiraTestStatist on start week calculation %s", currentLongTime()));
		Timer timer = new Timer();
		
		spiraTestOnStartWeekStatisticController.execute();
		
		LOG.info(format("SpiraTestStatist on start week calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
