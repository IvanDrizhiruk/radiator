package ua.dp.ardas.radiator.dto.buils.state;

import ua.dp.ardas.radiator.utils.JsonUtils;

public class Commiter {

	public String name;
	public String email;

	public Commiter(String name, String email) {
		this.name = name;
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("Commiter %s", JsonUtils.toJSON(this));
	}
}
