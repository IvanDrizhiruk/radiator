package ua.dp.ardas.radiator.resr.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import ua.dp.ardas.radiator.dto.BuildInfo;

public class RunRest {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/app-context.xml");
		
		RestTemplate restTemplate = context.getBean("restTemplate", RestTemplate.class);
		
		String lastFailedBuild = restTemplate.getForObject("http://hudson-2.devel.ardas.dp.ua:8080/job/broadcaster/lastFailedBuild/buildNumber", String.class);
		System.out.println(lastFailedBuild);

		String infoPath = "http://hudson-2.devel.ardas.dp.ua:8080/job/broadcaster/"+lastFailedBuild+"/api/json";
		System.out.println(infoPath);
		BuildInfo result = restTemplate.getForObject(infoPath, BuildInfo.class);
		System.out.println(result);
		
		
	}
}
