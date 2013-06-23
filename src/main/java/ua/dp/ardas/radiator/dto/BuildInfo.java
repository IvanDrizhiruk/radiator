package ua.dp.ardas.radiator.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonAutoDetect
@JsonRootName("actions")
public class BuildInfo {
	@JsonProperty("actions")
	public List<Action> actions;
	public List<String> artifacts;
	public Boolean building;
	public String description;
	public Integer duration;
	public String fullDisplayName;
	public String id;
	public Boolean keepLog;
	public Integer number;
	public String result;
	public String timestamp; //TODO ISD change type
	public String url;
	public String builtOn;
	public ChangeSet changeSet;
	public List<Culprits> culprits;

	@Override
	public String toString() {
		return "BuildInfo [actions=" + actions + ", artifacts=" + artifacts
				+ ", building=" + building + ", description=" + description
				+ ", duration=" + duration + ", fullDisplayName="
				+ fullDisplayName + ", id=" + id + ", keepLog=" + keepLog
				+ ", number=" + number + ", result=" + result + ", timestamp="
				+ timestamp + ", url=" + url + ", builtOn=" + builtOn
				+ ", changeSet=" + changeSet + ", culprits=" + culprits + "]";
	}
}
