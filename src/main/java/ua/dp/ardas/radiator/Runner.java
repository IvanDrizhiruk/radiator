package ua.dp.ardas.radiator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/app-context.xml");
	}
}