package ua.dp.ardas.radiator.dao;

import ua.dp.ardas.radiator.jobs.kanbanflow.Column;

import java.util.List;
import java.util.Map;

public interface KanbanFlowStatisticDAO {

	void insert(String boardName, List<Column> totalTaskCounts);

	Map<String, List<Column>> findAll();
}