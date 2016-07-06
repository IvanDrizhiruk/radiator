package ua.dp.ardas.radiator.web.rest.report;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.ardas.radiator.config.RadiatorProperties;
import ua.dp.ardas.radiator.config.RadiatorProperties.Kanbanflow.BoardConfig;
import ua.dp.ardas.radiator.domain.KanbanFlowBoard;
import ua.dp.ardas.radiator.repository.KanbanFlowBoardRepository;
import ua.dp.ardas.radiator.repository.KanbanFlowCellInfoRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing KanbanFlowCellInfo.
 */
@RestController
@RequestMapping("/api")
public class KanbanFlowReportResource {

    private final Logger log = LoggerFactory.getLogger(KanbanFlowReportResource.class);

    @Inject
    private KanbanFlowBoardRepository kanbanFlowBoardRepository;
    @Inject
    private KanbanFlowCellInfoRepository kanbanFlowCellInfoRepository;
    @Inject
    private RadiatorProperties radiatorProperties;
    

    /**
     * GET  /kanban-flow-cell-infos : get all the kanbanFlowCellInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kanbanFlowCellInfos in body
     */
    @RequestMapping(value = "/report/kanbanflow/last",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KanbanflowBoard> getLastKanbanFlow() {
        log.debug("REST request to get all KanbanFlowCellInfos");

        List<KanbanflowBoard> boardas = new ArrayList<>();

        for (BoardConfig boardConfig : radiatorProperties.kanbanflow.boardConfigs) {
            KanbanFlowBoard dbBoard = kanbanFlowBoardRepository.findOneByName(boardConfig.boardName) ;

            if (null != dbBoard) {
                KanbanflowBoard board = new KanbanflowBoard();
                board.boardName = boardConfig.boardName;
                board.cells = kanbanFlowCellInfoRepository.findLastKanbanFlowCells(dbBoard);

                boardas.add(board);
            }
        }

        return boardas;
    }
}
