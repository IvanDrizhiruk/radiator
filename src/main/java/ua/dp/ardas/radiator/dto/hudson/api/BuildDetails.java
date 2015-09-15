package ua.dp.ardas.radiator.dto.hudson.api;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;
import ua.dp.ardas.radiator.utils.JsonUtils;

import java.util.List;

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
	public Long timestamp;
	public String url;
	public String builtOn;
	public ChangeSet changeSet;
	public List<Person> culprits;
	public List<Runs> runs;

	@Override
	public String toString() {
		return String.format("BuildDetails %s", JsonUtils.toJSON(this));
	}
}
