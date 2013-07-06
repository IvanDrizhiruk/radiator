package ua.dp.ardas.radiator.dto.hudson.api;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Person {
	public String absoluteUrl;
	public String fullName;
	
	public Person() {}
	
	public Person(String absoluteUrl, String fullName) {
		this.absoluteUrl = absoluteUrl;
		this.fullName = fullName;
	}
}
