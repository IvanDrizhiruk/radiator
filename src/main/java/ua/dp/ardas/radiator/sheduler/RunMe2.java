package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.dao.ThucydidesTestStatusDAO;
import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatistic;



@Component
public class RunMe2 {
	private static Logger LOG = Logger.getLogger(RunMe2.class.getName());
	
	@Value("${worker2.name:RunMe2}")
	private String name;

	@Autowired
	private Worker worker;
	
	@Autowired
	private BuildStateDAO buildStateDAO;
	
	@Autowired
	private ThucydidesTestStatusDAO thucydidesTestStatusDAO;

	@Scheduled(fixedDelay=1000)
	private void  executeTask() {
		worker.doAction(name);
		
//		LOG.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//		for (BuildState state1 : buildStateDAO.findAll()) {
//			LOG.warn(state1);
//		}
//		LOG.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		LOG.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		for (ThucydidesTestStatistic state2 : thucydidesTestStatusDAO.findAll()) {
			LOG.warn(state2);
		}
		LOG.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}
