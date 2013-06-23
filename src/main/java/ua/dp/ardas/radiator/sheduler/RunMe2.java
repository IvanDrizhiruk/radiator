package ua.dp.ardas.radiator.sheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.Worker;


@Component
public class RunMe2 {
	@Value("${worker2.name:RunMe2}")
	private String name;
	
	@Autowired
	private Worker worker;

	//@Scheduled(fixedDelay=30000)
	private void  executeTask() {
		worker.doAction(name);
	}
}
