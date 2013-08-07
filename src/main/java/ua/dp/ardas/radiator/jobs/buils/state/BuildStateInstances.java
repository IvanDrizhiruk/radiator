package ua.dp.ardas.radiator.jobs.buils.state;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang.StringUtils.defaultString;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import ua.dp.ardas.radiator.config.AppConfig;

@Component
@Scope("prototype")
public class BuildStateInstances {
	
	@Autowired
	protected AppConfig config;
	@Value("${buildStale.instances.namesjobs}")
	protected String instancesString;
	@Value("${build.state.errorMessage:Build has been broken}")
	protected String defaultErrorMessage;
	
	private List<BuildStateInstance> instances;
	
	
	public List<BuildStateInstance> values() {
		if (null == instances) {
			instances = init(instancesString);
		}
		return instances;
	}
	
	private List<BuildStateInstance> init(String instancesString) {
		List<BuildStateInstance> instances = Lists.newArrayList();
		
		for (String instansName : splitToInstances(instancesString)) {
			instances.add(new BuildStateInstance(instansName,
					isInstanceConfigurationIssue(instansName),
					getConfigUrl(instansName),
					getErrorMessage(instansName)));
		}
		
		return instances;
	}

	private Iterable<String> splitToInstances(String instancesString) {
		return Splitter.on(',')
				.omitEmptyStrings()
				.trimResults()
				.split(defaultString(instancesString));
	}

	private boolean isInstanceConfigurationIssue(String instansName) {
		Boolean isCconfigurationIssue = config.boolenadProperty(format("build.state.%s.configuration.issue", instansName));
		
		return TRUE.equals(isCconfigurationIssue);
	}
	
	private String getConfigUrl(String instansesNames) {
		return config.stringProperty(format("build.state.%s.url", instansesNames));
	}
	
	private String getErrorMessage(String instansName) {
		String customErrorMessage = config.stringProperty(format("build.state.%s.errorMessage", instansName));
		
		return defaultIfEmpty(customErrorMessage, defaultErrorMessage);
	}
}
