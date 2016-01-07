package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.jobs.buils.state.BuildStateContorller;
import ua.dp.ardas.radiator.utils.Timer;

import javax.annotation.PostConstruct;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;


@Component
public class BuildStateScheduler {
	private static Logger LOG = Logger.getLogger(BuildStateScheduler.class.getName());
	
	@Autowired
	private BuildStateContorller buildStateContorller;
	
	@PostConstruct
	void initialize() {
		executeTask();
	}
	
	@Scheduled(cron="${build.stale.cron}")
	private void  executeTask() {
		LOG.info(format("Start BuildState calculation %s", currentLongTime()));
		Timer timer = new Timer();
		buildStateContorller.execute();

		LOG.info(format("BuildState calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
