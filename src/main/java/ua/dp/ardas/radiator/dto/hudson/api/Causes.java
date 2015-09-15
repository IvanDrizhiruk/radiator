package ua.dp.ardas.radiator.dto.hudson.api;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Causes {
	public String shortDescription;
	public String userName;
	public String upstreamBuild;
	public String upstreamProject;
	public String upstreamUrl;
    public String userId;
}
