package ua.dp.ardas.radiator.resr.client;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ThucydidesTestRestClient {
	private static Logger LOG = Logger.getLogger(ThucydidesTestRestClient.class.getName());
	
	@Autowired
	private RestTemplate template;

	public String loadTestReport(String url) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("Try load thucydides test report from url: ", url));
		}
		return template.getForObject(url, String.class);
	}
}
