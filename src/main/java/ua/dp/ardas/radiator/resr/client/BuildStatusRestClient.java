package ua.dp.ardas.radiator.resr.client;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.TypeUtils.toIntegerOrNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ua.dp.ardas.radiator.dto.hudson.api.BuildDetails;

@Component
public class BuildStatusRestClient {
	private static Logger LOG = Logger.getLogger(BuildStatusRestClient.class.getName());
	
	@Autowired
	private RestTemplate template;
	
	public Integer loadLastBuildNumber(String daseUrl) {
		return loadNumber(format("%s/lastBuild/buildNumber", daseUrl));
	}

	public Integer loadLastSuccessfulBuildNumber(String daseUrl) {
		return loadNumber(format("%s/lastSuccessfulBuild/buildNumber", daseUrl));
	}
	
	public Integer loadLastFailedBuildNumber(String daseUrl) {
		return loadNumber(format("%s/lastFailedBuild/buildNumber", daseUrl));
	}

	private Integer loadNumber(String fullUrl) {
		return toIntegerOrNull(loadString(fullUrl));
	}

	private String loadString(String fullUrl) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(format("Try load from url ", fullUrl));
		}
		
		return template.getForObject(fullUrl, String.class);
	}
	
	public BuildDetails loadBuildDetails(String daseUrl, Integer lastFailedBuild) {
		String fullUrl = format("%s/%d/api/json", daseUrl, lastFailedBuild);
		if (LOG.isDebugEnabled()) {
			LOG.debug(format("Try load from url ", fullUrl));
		}
		BuildDetails forObject = null;
		try {
			return forObject = template.getForObject(fullUrl, BuildDetails.class);
		} finally {
			if (LOG.isDebugEnabled()) {
				LOG.debug(format("BuildDetails from url %s loaded : %s", fullUrl, forObject));
			}
		}
	}
}

