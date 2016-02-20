package ua.dp.ardas.radiator.jobs.kanbanflow;

import ua.dp.ardas.radiator.dto.kanbanflow.TotalTaskCounts;

public class Row {
    public String swimlaneName;
    public TotalTaskCounts totals;

    public Row(String swimlaneName, TotalTaskCounts totals) {
        this.swimlaneName = swimlaneName;
        this.totals = totals;
    }
}
