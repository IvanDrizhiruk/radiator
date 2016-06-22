package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.jobs.kanbanflow.KanbanFlowStatisticContorller;
import ua.dp.ardas.radiator.utils.Timer;

import javax.annotation.PostConstruct;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;


@Component
public class KanbanFlowScheduler {
	private static Logger LOG = Logger.getLogger(KanbanFlowScheduler.class.getName());
	
	@Autowired
	private KanbanFlowStatisticContorller kanbanFlowStatisticContorller;
	
	@PostConstruct
	void initialize() {
		executeTask();
	}
	
//	@Scheduled(cron="${kanbanflow.cron}")
	private void  executeTask() {
		LOG.info(format("Start KanbanFlow calculation %s", currentLongTime()));
		Timer timer = new Timer();
		
//		kanbanFlowStatisticContorller.execute();
		
		LOG.info(format("KanbanFlow calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
