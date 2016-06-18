package ua.dp.ardas.radiator.jobs.buils.state;

public class BuildStateInstance {
	
	public String name;
	public boolean isConfigurationIssue;
	public String configUrl;
	public String errorMessage;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConfigurationIssue() {
		return isConfigurationIssue;
	}

	public void setIsConfigurationIssue(boolean isConfigurationIssue) {
		this.isConfigurationIssue = isConfigurationIssue;
	}

	public String getConfigUrl() {
		return configUrl;
	}

	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
