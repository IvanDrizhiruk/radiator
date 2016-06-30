package ua.dp.ardas.radiator.web.rest.report;

import ua.dp.ardas.radiator.domain.KanbanFlowCellInfo;

import java.util.List;

public class KanbanflowBoard {
    public String boardName;
    public List<KanbanFlowCellInfo> cells;

    @Override
    public String toString() {
        return "KanbanflowBoard{" +
                "boardName='" + boardName + '\'' +
                ", cells=" + cells +
                '}';
    }
}
