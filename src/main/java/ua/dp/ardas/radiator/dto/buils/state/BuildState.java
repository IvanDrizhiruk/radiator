package ua.dp.ardas.radiator.dto.buils.state;

import ua.dp.ardas.radiator.utils.JsonUtils;

public class BuildState {
	public String instancesName;
	public States state;
	public String errorMessage;
	public String failedEmail;
	public String failedName;

	public BuildState(States state, String instancesName) {
		this.state = state;
		this.instancesName = instancesName;
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