package ua.dp.ardas.radiator.dto.hudson.api;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {
	public List<Causes> causes;
	public List<Parameter> parameters;
}
