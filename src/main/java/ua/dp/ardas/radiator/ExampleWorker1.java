package ua.dp.ardas.radiator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;



@Component
public class ExampleWorker1 implements Worker {
	private static Logger LOG = Logger.getLogger(ExampleWorker1.class.getName());
	public void doAction(String id) {
		LOG.info(id);
	}
}
