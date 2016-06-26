package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.jobs.kanbanflow.KanbanFlowStatisticContorller;
import ua.dp.ardas.radiator.utils.Timer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;


@Component
public class KanbanFlowScheduler {
	private static Logger LOG = Logger.getLogger(KanbanFlowScheduler.class.getName());
	
	@Inject
	private KanbanFlowStatisticContorller kanbanFlowStatisticContorller;
	
	@PostConstruct
	void initialize() {
		try {
			executeTask();
		} catch (Exception e) {
			LOG.warn("Unable init KanbanFlow statistic.");
			if (LOG.isDebugEnabled()) {
				LOG.debug(String.format("Error: ", e));
			}
		}
	}
	
	//@Scheduled(cron="${radiator.kanbanflow.cron}")
	private void  executeTask() {
		LOG.info(format("Start KanbanFlow calculation %s", currentLongTime()));
		Timer timer = new Timer();
		
		kanbanFlowStatisticContorller.execute();
		
		LOG.info(format("KanbanFlow calculation finished %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
