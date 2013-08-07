package ua.dp.ardas.radiator.jobs.buils.state;

public class BuildStateInstance {
	
	public String name;
	public boolean isConfigurationIssue;
	public String configUrl;
	public String errorMessage;
	

	public BuildStateInstance() {
	}
	
	public BuildStateInstance(String name, boolean isConfigurationIssue, String configUrl, String errorMessage) {
		this.name = name;
		this.isConfigurationIssue = isConfigurationIssue;
		this.configUrl = configUrl;
		this.errorMessage = errorMessage;
	}
}
