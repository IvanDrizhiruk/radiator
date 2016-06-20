package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.dp.ardas.radiator.config.RadiatorProperties;
import ua.dp.ardas.radiator.jobs.buils.state.BuildStateContorller;
import ua.dp.ardas.radiator.utils.DataTimeUtils;
import ua.dp.ardas.radiator.utils.Timer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Service
public class BuildStateScheduler {
	private static Logger LOG = Logger.getLogger(BuildStateScheduler.class.getName());
	
	@Autowired
	private BuildStateContorller buildStateContorller;

	@Inject
	private RadiatorProperties radiatorProperties;
	
	@PostConstruct
	void initialize() {
		executeTask();
	}
	
	@Scheduled(cron="${radiator.buildState.cron}")
	private void  executeTask() {

		LOG.debug(String.format("Start BuildState calculation %s", DataTimeUtils.currentLongTime()));
		Timer timer = new Timer();
		buildStateContorller.execute();

		LOG.debug(String.format("BuildState calculation finished %s. Total time: %d miliseconds",
				DataTimeUtils.currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
