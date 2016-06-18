package ua.dp.ardas.radiator.dto.hudson.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Causes {
	public String shortDescription;
	public String userName;
	public String upstreamBuild;
	public String upstreamProject;
	public String upstreamUrl;
    public String userId;
}
