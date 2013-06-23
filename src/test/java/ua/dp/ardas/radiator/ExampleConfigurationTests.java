package ua.dp.ardas.radiator;

import static org.junit.Assert.assertNotNull;

import ua.dp.ardas.radiator.Worker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleConfigurationTests {
	
	@Autowired
	private Worker service;

	@Test
	public void testSimpleProperties() throws Exception {
		assertNotNull(service);
	}
	
}
