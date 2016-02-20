package ua.dp.ardas.radiator.dto.kanbanflow;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    public int totalSecondsSpent;
    public int totalSecondsEstimate;
}
