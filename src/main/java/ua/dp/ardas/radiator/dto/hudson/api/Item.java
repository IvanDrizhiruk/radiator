package ua.dp.ardas.radiator.dto.hudson.api;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Item {
	public String date; // TODO ISD change type
	public String timestamp;
	public String msg;
	public List<Path> paths;
	public Integer revision;
	public Integer commitId;
	public String user;
	public List<String> affectedPaths;
	public Person author;
}
