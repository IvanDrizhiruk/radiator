package ua.dp.ardas.radiator.dto.hudson.api;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class ChangeSet {
	public List<Item> items;
	public String kind;
	public List<Revision> revisions;
	
	@Override
	public String toString() {
		return "ChangeSet [items=" + items + ", kind=" + kind + ", revisions="
				+ revisions + "]";
	}
}
