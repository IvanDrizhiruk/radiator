package ua.dp.ardas.radiator.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Item {
	public String date; // TODO ISD change type
	public String msg;
	public List<Path> paths;
	public Integer revision;
	public String user;
	
	@Override
	public String toString() {
		return "Item [date=" + date + ", msg=" + msg + ", paths=" + paths
				+ ", revision=" + revision + ", user=" + user + "]";
	}
}
