package ua.dp.ardas.radiator;

import ua.dp.ardas.radiator.sheduler.ExampleWorker1;
import junit.framework.TestCase;

public class ExampleServiceTests extends TestCase {

	private ExampleWorker1 service = new ExampleWorker1();
	
	public void testReadOnce() throws Exception {
		service.doAction("test");
		assertEquals("Hello world!", "Hello world!");
	}

}
