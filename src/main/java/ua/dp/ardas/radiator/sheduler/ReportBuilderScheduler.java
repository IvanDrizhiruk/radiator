package ua.dp.ardas.radiator.sheduler;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.DataTimeUtils.currentLongTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.report.builder.ReportBuilder;
import ua.dp.ardas.radiator.utils.Timer;

@Component
public class ReportBuilderScheduler {
	private static Logger LOG = Logger.getLogger(ReportBuilderScheduler.class.getName());
	
	@Autowired
	private ReportBuilder reportBuilder;
	
	@Scheduled(fixedDelay=600000)
	private void  executeTask() {
		LOG.info(format("Start builder of report %s", currentLongTime()));
		Timer timer = new Timer();
		
		reportBuilder.build();
		
		LOG.info(format("Build of report was finished at %s. Total time: %d miliseconds",
				currentLongTime(), timer.elapsedTimeInMilliseconds()));
	}
}
