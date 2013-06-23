package ua.dp.ardas.radiator.sheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.Worker;


@Component
public class RunMe1 {
	
	@Value("${worker1.name:RunMe1}")
	private int name;
	@Autowired
	private Worker worcker;

	
	//@Scheduled(fixedDelay=50000)
	private void  executeTask() {
		worcker.doAction(String.valueOf(name));
	}
}
