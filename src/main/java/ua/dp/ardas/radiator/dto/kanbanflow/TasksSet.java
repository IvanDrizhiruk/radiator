package ua.dp.ardas.radiator.dto.kanbanflow;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class TasksSet {
    public String columnName;
    public List<Task> tasks;
    public String swimlaneName;

    //For load unloaded tasks
    public String columnId;
    public String swimlaneId;
    public Boolean tasksLimited;
    public String nextTaskId;
}
