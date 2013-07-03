package ua.dp.ardas.radiator.sheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.buils.state.BuildStateContorller;
import ua.dp.ardas.radiator.jobs.thucydides.test.result.ThucydidesTestStatusContorller;


@Component
public class RunMe1 {
	
	@Value("${worker1.name:RunMe1}")
	private String name;
	@Autowired
	private Worker worcker;
	@Autowired
	private BuildStateContorller buildStateContorller;
	@Autowired
	private ThucydidesTestStatusContorller thucydidesTestStatusContorller;
	

	
	@Scheduled(fixedDelay=5000)
	private void  executeTask() {
		worcker.doAction(String.valueOf(name));
//		buildStateContorller.execute();
		
		thucydidesTestStatusContorller.execute();
	}
}