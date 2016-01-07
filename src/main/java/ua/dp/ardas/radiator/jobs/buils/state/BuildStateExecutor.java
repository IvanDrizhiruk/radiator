package ua.dp.ardas.radiator.jobs.buils.state;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.config.AppConfig;
import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.dto.hudson.api.BuildDetails;
import ua.dp.ardas.radiator.resr.client.BuildStatusRestClient;
import ua.dp.ardas.radiator.utils.BuildStateUtils;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.dto.buils.state.BuildState.States.*;

@Component
public class BuildStateExecutor {
	private static Logger LOG = Logger.getLogger(BuildStateExecutor.class.getName());
	
	@Autowired
	protected AppConfig config;
	@Autowired
	protected BuildStatusRestClient restClient;
	@Value("${faild.email.format:%s@ardas.dp.ua}")
	protected String emailFormat;

	
	public BuildState loadState(BuildStateInstance instances) {
		String url = instances.configUrl;
		if (LOG.isDebugEnabled()) {
			LOG.debug(format("url ", url));
		}

		BuildState buildState = null;
		try {
			buildState = calculateState(instances, url);
		} catch (Exception exception) {
			LOG.error("Can not evaluate buildState");
			if (LOG.isDebugEnabled()) {
				LOG.debug("Error", exception);
			}
		}
		
		if (LOG.isInfoEnabled()) {
			LOG.info(format("BuildState for url %s : %s", url, buildState));
		}
		
		return buildState;
	}

	protected BuildState calculateState(BuildStateInstance instances, String url) {
		int lastBuild = restClient.loadLastBuildNumber(url);
		Long lastRunTimestemp = getLastRunTimestemp(url, lastBuild);
		int lastSuccessfulBuild = restClient.loadLastSuccessfulBuildNumber(url, lastBuild);
		int lastFailedBuild = restClient.loadLastFailedBuildNumber(url, 0);


		if(lastSuccessfulBuild > lastFailedBuild) {
			return newSuccessState(instances, lastRunTimestemp);
		}
		
		if(lastFailedBuild == lastBuild && !instances.isConfigurationIssue) {
			return newBuildFailedState(instances, url, lastFailedBuild, lastRunTimestemp);
		}

		return newConfigurationFailedState(instances, lastRunTimestemp);
	}

	private BuildState newConfigurationFailedState(BuildStateInstance instances, Long lastRunTimestemp) {
		BuildState buildState = new BuildState(CONFIGURATION_FAILED, instances.name);
		buildState.lastRunTimestemp = lastRunTimestemp;

		return buildState;
	}

	private Long getLastRunTimestemp(String url, int lastBuild) {
		BuildDetails buildDetails = restClient.loadBuildDetails(url, lastBuild);

		return buildDetails.timestamp;
	}

	private BuildState newBuildFailedState(BuildStateInstance instance, String url, Integer lastFailedBuild, Long lastRunTimestemp) {
		BuildDetails buildDetails = restClient.loadBuildDetails(url, lastFailedBuild);

		BuildState buildState = new BuildState(BUILD_FAILED, instance.name);
		buildState.errorMessage = instance.errorMessage;
		buildState.commiters = BuildStateUtils.calculateCommiters(buildDetails.culprits, emailFormat);
		buildState.lastRunTimestemp = lastRunTimestemp;
		
		return buildState;
	}
	

	private BuildState newSuccessState(BuildStateInstance instance, Long lastRunTimestemp) {
		BuildState buildState = new BuildState(SUCCESS, instance.name);
		buildState.lastRunTimestemp = lastRunTimestemp;
		return buildState;

	}
}
