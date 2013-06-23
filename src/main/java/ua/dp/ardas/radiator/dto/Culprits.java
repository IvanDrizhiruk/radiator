package ua.dp.ardas.radiator.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Culprits {
	public String absoluteUrl;
	public String fullName;
	
	@Override
	public String toString() {
		return "Culprits [absoluteUrl=" + absoluteUrl + ", fullName="
				+ fullName + "]";
	}
}
