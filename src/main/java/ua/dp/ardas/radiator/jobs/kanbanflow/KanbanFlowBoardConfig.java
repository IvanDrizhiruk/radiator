package ua.dp.ardas.radiator.jobs.kanbanflow;

import java.util.Set;

public class KanbanFlowBoardConfig {
    public String instansName;
    public String url;
    public String token;
    public Set<Integer> columnNumbers;

    public KanbanFlowBoardConfig(String instansName, String configUrl, String configToken, Set<Integer> columnNumbers) {
        this.instansName = instansName;
        this.url = configUrl;
        this.token = configToken;
        this.columnNumbers = columnNumbers;
    }
}
