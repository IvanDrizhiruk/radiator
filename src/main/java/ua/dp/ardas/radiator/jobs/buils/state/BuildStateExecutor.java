package ua.dp.ardas.radiator.jobs.buils.state;

import static java.lang.String.format;
import static ua.dp.ardas.radiator.jobs.buils.state.BuildState.States.BUILD_FAILED;
import static ua.dp.ardas.radiator.jobs.buils.state.BuildState.States.CONFIGURATION_FAILED;
import static ua.dp.ardas.radiator.jobs.buils.state.BuildState.States.SUCCESS;
import static ua.dp.ardas.radiator.utils.BuildStateUtils.calculateFailedEmail;
import static ua.dp.ardas.radiator.utils.BuildStateUtils.calculateFailedName;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.config.AppConfig;
import ua.dp.ardas.radiator.dto.hudson.api.BuildDetails;
import ua.dp.ardas.radiator.resr.client.BuildStatusRestClient;

@Component
public class BuildStateExecutor {
	private static Logger LOG = Logger.getLogger(BuildStateExecutor.class.getName());
	
	@Autowired
	private AppConfig config;
	@Autowired
	private BuildStatusRestClient restClient;

	
	public BuildState loadState(BuildStateInstances instances) {
		String url = getUrl(instances);
		if (LOG.isDebugEnabled()) {
			LOG.debug(format("url ", url));
		}

		BuildState buildState = null;
		try {
			Integer lastBuild = restClient.loadLastBuildNumber(url);
			if(null == lastBuild) {
				LOG.error("Can not load last build number");
				
				return buildState = new BuildState(CONFIGURATION_FAILED);
			}
				
			Integer lastSuccessfulBuild = restClient.loadLastSuccessfulBuildNumber(url);
			Integer lastFailedBuild = restClient.loadLastFailedBuildNumber(url);
			
			
			if(isConfigurationError(lastBuild, lastSuccessfulBuild, lastFailedBuild)) {
				return buildState = new BuildState(CONFIGURATION_FAILED);
			} else if(isBuildError(lastBuild, lastSuccessfulBuild, lastFailedBuild)){
				BuildDetails buildDetails = restClient.loadBuildDetails(url, lastFailedBuild);
				
				buildState = new BuildState(BUILD_FAILED);
				buildState.errorMessage = getErrorMessage(instances);
				buildState.failedEmail = calculateFailedEmail(buildDetails.culprits);
				buildState.failedName = calculateFailedName(buildDetails.culprits);
						
				
				return buildState;
			}
			
			return buildState = new BuildState(SUCCESS);
		} finally {
			if (LOG.isInfoEnabled()) {
				LOG.info(format("BuildState for url %s : %s", url, buildState));
			}
		}
	}

	private boolean isConfigurationError(Integer lastBuild, Integer lastSuccessfulBuild, Integer lastFailedBuild) {
		return (!lastBuild.equals(lastSuccessfulBuild)
				&& !lastBuild.equals(lastFailedBuild));
	}



	private boolean isBuildError(Integer lastBuild, Integer lastSuccessfulBuild, Integer lastFailedBuild) {
		return (!lastBuild.equals(lastFailedBuild));
	}



	private String getUrl(BuildStateInstances instances) {
		return config.stringProperty(format("job.%s.url", instances));
	}
	
	private String getErrorMessage(BuildStateInstances instances) {
		return config.stringProperty(format("job.%s.errorMessage", instances));
	}
}
