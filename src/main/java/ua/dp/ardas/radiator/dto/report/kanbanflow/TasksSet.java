package ua.dp.ardas.radiator.dto.report.kanbanflow;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @Override
    public String toString() {
        return "TasksSet{" +
                "columnName='" + columnName + '\'' +
                ", tasks=" + tasks +
                ", swimlaneName='" + swimlaneName + '\'' +
                ", columnId='" + columnId + '\'' +
                ", swimlaneId='" + swimlaneId + '\'' +
                ", tasksLimited=" + tasksLimited +
                ", nextTaskId='" + nextTaskId + '\'' +
                '}';
    }
}
