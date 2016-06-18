package ua.dp.ardas.radiator.restclient;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.config.RadiatorProperties;
import ua.dp.ardas.radiator.dto.hudson.api.BuildDetails;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.utils.TypeUtils.toIntegerOrNull;

@Component
public class BuildStatusRestClient {
	private static Logger LOG = Logger.getLogger(BuildStatusRestClient.class.getName());

	@Inject
	private RadiatorProperties properties;

	private BasicAutorisationRestClient restClient;

	@PostConstruct
	public void init() {
		if (null == restClient) {
			restClient = new BasicAutorisationRestClient(properties.buildState.auth.username, properties.buildState.auth.password);
		}
	}

	public Integer loadLastBuildNumber(String daseUrl) {
		return loadNumber(format("%s/lastBuild/buildNumber", daseUrl));
	}

	public Integer loadLastSuccessfulBuildNumber(String daseUrl) {
		return loadNumber(format("%s/lastSuccessfulBuild/buildNumber", daseUrl));
	}

	public Integer loadLastSuccessfulBuildNumber(String daseUrl, int defaultValue) {
		Integer LastSuccessfulBuildNumber = loadLastSuccessfulBuildNumber(daseUrl);
		return null == LastSuccessfulBuildNumber
				? defaultValue
				: LastSuccessfulBuildNumber;
	}
	
	public Integer loadLastFailedBuildNumber(String daseUrl) {
		return loadNumber(format("%s/lastFailedBuild/buildNumber", daseUrl));
	}

	public Integer loadLastFailedBuildNumber(String daseUrl, int defaultValue) {
		Integer lastFailedBuildNumber = loadLastFailedBuildNumber(daseUrl);
		return null == lastFailedBuildNumber
				? defaultValue
				: lastFailedBuildNumber;
	}

	private Integer loadNumber(String fullUrl) {
		return toIntegerOrNull(loadString(fullUrl));
	}

	private String loadString(String fullUrl) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(format("Try load from url ", fullUrl));
		}

		try {
			return restClient.getForObject(fullUrl, String.class);
		} catch (Exception e) {
			return null;
		}
	}


	public BuildDetails loadBuildDetails(String daseUrl, Integer lastFailedBuild) {
		String fullUrl = format("%s/%d/api/json", daseUrl, lastFailedBuild);
		if (LOG.isDebugEnabled()) {
			LOG.debug(format("Try load from url ", fullUrl));
		}
		BuildDetails forObject = null;
		try {
			//System.out.println(restClient.getForObject(fullUrl, String.class));

			return forObject = restClient.getForObject(fullUrl, BuildDetails.class);
//		} catch(Exception e) {
//			e.printStackTrace();
		} finally {
			if (LOG.isDebugEnabled()) {
				LOG.debug(format("BuildDetails from url %s loaded : %s", fullUrl, forObject));
			}
		}
	}
}
