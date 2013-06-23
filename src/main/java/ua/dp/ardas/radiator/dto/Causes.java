package ua.dp.ardas.radiator.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Causes {
	public String shortDescription;

	@Override
	public String toString() {
		return "Causes [shortDescription=" + shortDescription + "]";
	}
}
