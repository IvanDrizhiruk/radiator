package ua.dp.ardas.radiator.restclient;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ThucydidesTestRestClient {
	private static Logger LOG = Logger.getLogger(ThucydidesTestRestClient.class.getName());
	
//	@Inject
//	private RestTemplate template;

	public String loadTestReport(String url) {
//        try {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug(String.format("Try load thucydides test report from url: ", url));
//            }
//            return template.getForObject(url, String.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return "";
	}
}
