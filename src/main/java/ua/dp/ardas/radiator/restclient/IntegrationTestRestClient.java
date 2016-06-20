package ua.dp.ardas.radiator.restclient;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class IntegrationTestRestClient {
	private static Logger LOG = Logger.getLogger(IntegrationTestRestClient.class.getName());

	private RestTemplate restClient;


	@PostConstruct
	public void init() {
		if (null == restClient) {
			restClient = new RestTemplate();
		}
	}

	public String loadTestReport(String url) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("Try load thucydides test report from url: ", url));
            }
            return restClient.getForObject(url, String.class);
        } catch (Exception e) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(String.format("Unable load integration test result by url: ", url));
			}
        }

        return "";
	}
}
