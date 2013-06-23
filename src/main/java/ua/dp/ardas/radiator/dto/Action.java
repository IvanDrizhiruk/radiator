package ua.dp.ardas.radiator.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Action {
	public List<Causes> causes;

	@Override
	public String toString() {
		return "Action [causes=" + causes + "]";
	}
}
