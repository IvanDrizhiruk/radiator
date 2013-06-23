package ua.dp.ardas.radiator.dto.hudson.api;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import ua.dp.ardas.radiator.utils.JsonUtils;

@JsonAutoDetect
@JsonRootName("actions")
public class BuildDetails {
	@JsonProperty("actions")
	public List<Action> actions;
	public List<String> artifacts;
	public Boolean building;
	public String description;
	public Integer duration;
	public Integer estimatedDuration;
	public Integer executor;
	public String fullDisplayName;
	public String id;
	public Boolean keepLog;
	public Integer number;
	public String result;
	public String timestamp; // TODO ISD change type
	public String url;
	public String builtOn;
	public ChangeSet changeSet;
	public List<Person> culprits;

	@Override
	public String toString() {
		return String.format("BuildDetails %s", JsonUtils.toJSON(this));
	}
}
