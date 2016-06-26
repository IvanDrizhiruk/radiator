package ua.dp.ardas.radiator.dto.report.kanbanflow;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    public int totalSecondsSpent;
    public int totalSecondsEstimate;
}
