package ua.dp.ardas.radiator.jobs.kanbanflow;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.dao.KanbanFlowStatisticDAO;
import ua.dp.ardas.radiator.dto.kanbanflow.Task;
import ua.dp.ardas.radiator.dto.kanbanflow.TasksSet;
import ua.dp.ardas.radiator.dto.kanbanflow.TotalTaskCounts;
import ua.dp.ardas.radiator.resr.client.kanbanflow.KanbanFlowRestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class KanbanFlowStatisticContorller {
    private static Logger LOG = Logger.getLogger(KanbanFlowStatisticContorller.class.getName());

    @Autowired
    private KanbanFlowRestClient restClient;
    @Autowired
    private KanbanFlowStatisticDAO dao;
    @Autowired
    private KanbanFlowBoardsInstances kanbanFlowBoardsInstances;

    public void execute() {
        for (KanbanFlowBoardConfig boardConfig : kanbanFlowBoardsInstances.getBoardsInstances()) {
            try {
                List<TasksSet> taskSet = restClient.loadTaskSets(boardConfig.url, boardConfig.token);
                List<Column> totalTaskCounts = prepareTotalStatistic(taskSet);
                List<Column> totalTaskCountsForShow = filterTotalStatistic(totalTaskCounts, boardConfig.columnNumbers);

                dao.insert(boardConfig.instansName, totalTaskCountsForShow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<Column> filterTotalStatistic(List<Column> totalTaskCounts, Set<Integer> availableColumnNumbers) {
        List<Column> res = Lists.newArrayList();

        ArrayList<Integer> sortedColumnNumbers = Lists.newArrayList(availableColumnNumbers);
        Collections.sort(sortedColumnNumbers);

        for (int index : sortedColumnNumbers) {
            if (totalTaskCounts.size() >= index) {
                Column column = totalTaskCounts.get(index - 1);
                res.add(column);
            }
        }

        return res;
    }


    private List<Column> prepareTotalStatistic(List<TasksSet> tasks) {
        List<Column> res = Lists.newArrayList();

        Column currentColumn = null;
        for (TasksSet taskSet : tasks) {
            String columnName = taskSet.columnName;

            if (null == currentColumn || !currentColumn.columnName.equals(columnName)) {
                currentColumn = new Column(columnName);
                res.add(currentColumn);
            }

            if (currentColumn.columnName.equals(columnName)) {
                Row newRow = new Row(taskSet.swimlaneName, calculateTotalTaskCount(taskSet.tasks));
                currentColumn.rows.add(newRow);
            }
        }

        return res;
    }

    private TotalTaskCounts calculateTotalTaskCount(List<Task> tasks) {
        TotalTaskCounts tasksCounts = new TotalTaskCounts();

        if (null == tasks || tasks.isEmpty()) {
            return tasksCounts;
        }

        for (Task task : tasks) {
            tasksCounts.totalSecondsEstimate += task.totalSecondsEstimate;
            tasksCounts.totalSecondsSpent += task.totalSecondsSpent;
        }

        return tasksCounts;
    }
}
