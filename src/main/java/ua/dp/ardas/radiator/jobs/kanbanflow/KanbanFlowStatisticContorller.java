package ua.dp.ardas.radiator.jobs.kanbanflow;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.config.RadiatorProperties;
import ua.dp.ardas.radiator.config.RadiatorProperties.Kanbanflow.BoardConfig;
import ua.dp.ardas.radiator.domain.KanbanFlowBoard;
import ua.dp.ardas.radiator.domain.KanbanFlowCellInfo;
import ua.dp.ardas.radiator.domain.KanbanFlowColumn;
import ua.dp.ardas.radiator.domain.KanbanFlowSwimlane;
import ua.dp.ardas.radiator.dto.report.kanbanflow.Task;
import ua.dp.ardas.radiator.dto.report.kanbanflow.TasksSet;
import ua.dp.ardas.radiator.repository.KanbanFlowBoardRepository;
import ua.dp.ardas.radiator.repository.KanbanFlowCellInfoRepository;
import ua.dp.ardas.radiator.repository.KanbanFlowColumnRepository;
import ua.dp.ardas.radiator.repository.KanbanFlowSwimlaneRepository;
import ua.dp.ardas.radiator.restclient.KanbanFlowRestClient;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class KanbanFlowStatisticContorller {
    private static Logger LOG = Logger.getLogger(KanbanFlowStatisticContorller.class.getName());

    @Inject
    private KanbanFlowRestClient restClient;
    @Inject
    private KanbanFlowBoardRepository kanbanFlowBoardRepository;
    @Inject
    private KanbanFlowCellInfoRepository kanbanFlowCellInfoRepository;
    @Inject
    private KanbanFlowColumnRepository kanbanFlowColumnRepository;
    @Inject
    private KanbanFlowSwimlaneRepository kanbanFlowSwimlaneRepository;
    @Inject
    private RadiatorProperties properties;

    public void execute() {
        for (BoardConfig boardConfig : properties.kanbanflow.boardConfigs) {
            try {
                processBoard(boardConfig);
            } catch (Exception e) {
               LOG.warn("Board " + boardConfig.boardName + " was not processed", e);
            }
        }
    }

    private void processBoard(BoardConfig boardConfig) {
        List<TasksSet> taskSet = restClient.loadTaskSets(boardConfig.url, boardConfig.token);
        List<KanbanFlowCellInfo> cellsInfos = transformToStatisticInfo(taskSet);

        List<KanbanFlowColumn> columns = extractColumnsWithIndexes(cellsInfos);
        List<KanbanFlowSwimlane> swimlanes = extractSwimlaneWithIndexes(cellsInfos);

        List<KanbanFlowColumn> filteredColumns = filterAvailableColumns(columns, boardConfig.columnNumbers);

        KanbanFlowBoard board = loadOrCreateBoard(boardConfig.boardName);
        Map<String, KanbanFlowColumn> dbcolumns = loadOrCreateColumns(filteredColumns);
        Map<String, KanbanFlowSwimlane> dbswimlanes = loadOrCreateSwimlanes(swimlanes);


        List<KanbanFlowCellInfo> cellsForSave = cellsInfos.stream()
                .map(cell -> updateDBObjectsInCellOrNull(cell, board, dbcolumns, dbswimlanes))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        kanbanFlowCellInfoRepository.save(cellsForSave);
    }

    private KanbanFlowCellInfo updateDBObjectsInCellOrNull(KanbanFlowCellInfo cell, KanbanFlowBoard board, Map<String,
            KanbanFlowColumn> dbcolumns, Map<String, KanbanFlowSwimlane> dbswimlanes) {

        KanbanFlowColumn dbColumn = dbcolumns.get(cell.getColumn().getName());
        KanbanFlowSwimlane dbSwimlane = dbswimlanes.get(cell.getSwimlane().getName());

        if (null == dbColumn || null == dbSwimlane) {
            return null;
        }

        cell.setBoard(board);
        cell.setColumn(dbColumn);
        cell.setSwimlane(dbSwimlane);

        return cell;
    }

    private KanbanFlowBoard loadOrCreateBoard(String boardName) {
        KanbanFlowBoard board = kanbanFlowBoardRepository.findOneByName(boardName);

        if (null != board) {
            return board;
        }

        KanbanFlowBoard newBoard = new KanbanFlowBoard();
        newBoard.setName(boardName);
        
        return kanbanFlowBoardRepository.save(newBoard);
    }

    private Map<String, KanbanFlowSwimlane> loadOrCreateSwimlanes(List<KanbanFlowSwimlane> swimlanes) {
        return swimlanes.stream()
                .map(this::loadOrCreateSwimlanes)
                .collect(Collectors.toMap(KanbanFlowSwimlane::getName, swimlane -> swimlane));
    }

    private KanbanFlowSwimlane loadOrCreateSwimlanes(KanbanFlowSwimlane kanbanFlowSwimlane) {
        KanbanFlowSwimlane swimlane = kanbanFlowSwimlaneRepository.findOneByNameAndIndexNumber(
                kanbanFlowSwimlane.getName(), kanbanFlowSwimlane.getIndexNumber());

        return (null == swimlane)
                ? kanbanFlowSwimlaneRepository.save(kanbanFlowSwimlane)
                : swimlane;
    }

    private Map<String, KanbanFlowColumn> loadOrCreateColumns(List<KanbanFlowColumn> columns) {
        return columns.stream()
                .collect(Collectors.toMap(KanbanFlowColumn::getName, this::loadOrCreateColumn));
    }

    private KanbanFlowColumn loadOrCreateColumn(KanbanFlowColumn kanbanFlowColumn) {
        KanbanFlowColumn column = kanbanFlowColumnRepository.findOneByNameAndIndexNumber(
                kanbanFlowColumn.getName(), kanbanFlowColumn.getIndexNumber());

        return (null == column)
            ? kanbanFlowColumnRepository.save(kanbanFlowColumn)
                : column;
    }

    private List<KanbanFlowColumn> filterAvailableColumns(List<KanbanFlowColumn> columns, List<Integer> columnNumbers) {
        return columns.stream()
                .filter(column -> columnNumbers.contains(column.getIndexNumber()))
                .collect(Collectors.toList());
    }

    private List<KanbanFlowSwimlane> extractSwimlaneWithIndexes(List<KanbanFlowCellInfo> cellsInfos) {
        if (null == cellsInfos && cellsInfos.isEmpty()) {
            return null;
        }

        String curentColumn = cellsInfos.get(0).getColumn().getName();
        if (null == curentColumn) {
            return null;
        }

        AtomicInteger indexNumber = new AtomicInteger();
        return cellsInfos.stream()
                .filter(celInfo -> curentColumn.equals(celInfo.getColumn().getName()))
                .map(celInfo -> celInfo.getSwimlane())
                .peek(swimlane -> swimlane.setIndexNumber(indexNumber.getAndIncrement()))
                .collect(Collectors.toList());
    }

    private List<KanbanFlowColumn> extractColumnsWithIndexes(List<KanbanFlowCellInfo> cellsInfos) {
        if (null == cellsInfos && cellsInfos.isEmpty()) {
            return null;
        }

        String curentSwimlane = cellsInfos.get(0).getSwimlane().getName();
        if (null == curentSwimlane) {
            return null;
        }

        AtomicInteger indexNumber = new AtomicInteger();
        return cellsInfos.stream()
                .filter(celInfo -> curentSwimlane.equals(celInfo.getSwimlane().getName()))
                .map(celInfo -> celInfo.getColumn())
                .peek(column -> column.setIndexNumber(indexNumber.getAndIncrement()))
                .collect(Collectors.toList());
    }

    private List<KanbanFlowCellInfo> transformToStatisticInfo(List<TasksSet> tasks) {
        return tasks.stream()
                .map(this::toCellInfo)
                .collect(Collectors.toList());
    }

    private KanbanFlowCellInfo toCellInfo(TasksSet taskSet) {
        KanbanFlowCellInfo currentCell = new KanbanFlowCellInfo();
        currentCell.setColumn(toColumn(taskSet.columnName));
        currentCell.setSwimlane(toSwimlane(taskSet.swimlaneName));

        TotalTaskCounts totalCounts = calculateTotalTaskCount(taskSet.tasks);
        currentCell.setTotalSecondsSpent(new Long(totalCounts.totalSecondsSpent));
        currentCell.setTotalSecondsEstimated(new Long(totalCounts.totalSecondsEstimate));

        return currentCell;
    }

    private KanbanFlowSwimlane toSwimlane(String swimlaneName) {
        KanbanFlowSwimlane swimlane = new KanbanFlowSwimlane();
        swimlane.setName(swimlaneName);
        return swimlane;
    }

    private KanbanFlowColumn toColumn(String columnName) {
        KanbanFlowColumn column = new KanbanFlowColumn();
        column.setName(columnName);
        return column;
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

    private class TotalTaskCounts {
        public int totalSecondsEstimate;
        public int totalSecondsSpent;
    }
}
