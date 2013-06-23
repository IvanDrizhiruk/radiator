package ua.dp.ardas.radiator.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Revision {
	public String module;
	public Integer revision;
	
	@Override
	public String toString() {
		return "Revision [module=" + module + ", revision=" + revision + "]";
	}
}
