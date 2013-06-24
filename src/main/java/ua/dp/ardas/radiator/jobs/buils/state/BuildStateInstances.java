package ua.dp.ardas.radiator.jobs.buils.state;

public enum BuildStateInstances {
	UI,
	WS,
	UI_THUCYDIDES_TESTS1(true),
	UI_THUCYDIDES_TESTS2(true),
	UI_THUCYDIDES_TESTS3,
	UI_THUCYDIDES_TESTS4(true);
	
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
