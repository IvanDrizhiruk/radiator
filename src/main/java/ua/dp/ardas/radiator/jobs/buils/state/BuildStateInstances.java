package ua.dp.ardas.radiator.jobs.buils.state;

public enum BuildStateInstances {
	UI,
	WS,
	UI_THUCYDIDES_TESTS(true);
	
	private final boolean isConfigurationIssue;

	BuildStateInstances() {
		this.isConfigurationIssue = false;
	}
	
	BuildStateInstances(boolean isConfigurationIssue) {
		this.isConfigurationIssue = isConfigurationIssue;
		
	}

	public boolean isConfigurationIssue() {
		return isConfigurationIssue;
	}
}
