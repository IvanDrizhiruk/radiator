package ua.dp.ardas.radiator.dao.impl;

import com.google.common.collect.Maps;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.dao.KanbanFlowStatisticDAO;
import ua.dp.ardas.radiator.jobs.kanbanflow.Column;

import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class KanbanFlowStatisticDAOInMemory implements KanbanFlowStatisticDAO {

    private Map<String, List<Column>> kanbanFlowStatistic = Maps.newHashMap();


    @Override
    public void insert(String boardName, List<Column> totalTaskCounts) {
        if (null == totalTaskCounts) {
            return;
        }

        kanbanFlowStatistic.put(boardName, totalTaskCounts);
    }

    @Override
    public Map<String, List<Column>> findAll() {
        return kanbanFlowStatistic;
    }
}