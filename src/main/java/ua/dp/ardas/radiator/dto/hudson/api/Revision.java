package ua.dp.ardas.radiator.dto.hudson.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Revision {
	public String module;
	public Integer revision;
	
	@Override
	public String toString() {
		return "Revision [module=" + module + ", revision=" + revision + "]";
	}
}
