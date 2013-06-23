package ua.dp.ardas.radiator;

import org.springframework.stereotype.Component;



@Component
public class ExampleWorker1 implements Worker {
	
	public void doAction(String id) {
		System.out.println(id);
	}
}
