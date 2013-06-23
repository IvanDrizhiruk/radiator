package ua.dp.ardas.radiator.dto.hudson.api;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Action {
	public List<Causes> causes;
	public List<Parameter> parameters;
}
