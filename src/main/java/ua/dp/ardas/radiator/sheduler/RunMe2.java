package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.jobs.buils.state.BuildState;



@Component
public class RunMe2 {
	private static Logger LOG = Logger.getLogger(RunMe2.class.getName());
	
	@Value("${worker2.name:RunMe2}")
	private String name;

	@Autowired
	private Worker worker;
	
	@Autowired
	private BuildStateDAO dao;

	@Scheduled(fixedDelay=10000)
	private void  executeTask() {
		worker.doAction(name);
		
		LOG.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		for (BuildState state : dao.findAll()) {
			LOG.warn(state);
		}
		LOG.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}
