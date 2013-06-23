package ua.dp.ardas.radiator.jobs.buils.state;

import ua.dp.ardas.radiator.utils.JsonUtils;



public class BuildState {
	States state;
	public String errorMessage;
	public String failedEmail;
	public String failedName;

	public BuildState(States state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return String.format("BuildState %s", JsonUtils.toJSON(this));
	}
	
	public enum States {
		BUILD_FAILED,
		CONFIGURATION_FAILED,
		SUCCESS;
	}
}