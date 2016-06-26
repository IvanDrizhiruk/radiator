package ua.dp.ardas.radiator.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dp.ardas.radiator.domain.KanbanFlowBoard;
import ua.dp.ardas.radiator.repository.KanbanFlowBoardRepository;
import ua.dp.ardas.radiator.web.rest.util.HeaderUtil;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing KanbanFlowBoard.
 */
@RestController
@RequestMapping("/api")
public class KanbanFlowBoardResource {

    private final Logger log = LoggerFactory.getLogger(KanbanFlowBoardResource.class);
        
    @Inject
    private KanbanFlowBoardRepository kanbanFlowBoardRepository;

    /**
     * POST  /kanban-flow-boards : Create a new kanbanFlowBoard.
     *
     * @param kanbanFlowBoard the kanbanFlowBoard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kanbanFlowBoard, or with status 400 (Bad Request) if the kanbanFlowBoard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kanban-flow-boards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowBoard> createKanbanFlowBoard(@RequestBody KanbanFlowBoard kanbanFlowBoard) throws URISyntaxException {
        log.debug("REST request to save KanbanFlowBoard : {}", kanbanFlowBoard);
        if (kanbanFlowBoard.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kanbanFlowBoard", "idexists", "A new kanbanFlowBoard cannot already have an ID")).body(null);
        }
        KanbanFlowBoard result = kanbanFlowBoardRepository.save(kanbanFlowBoard);
        return ResponseEntity.created(new URI("/api/kanban-flow-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kanbanFlowBoard", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kanban-flow-boards : Updates an existing kanbanFlowBoard.
     *
     * @param kanbanFlowBoard the kanbanFlowBoard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kanbanFlowBoard,
     * or with status 400 (Bad Request) if the kanbanFlowBoard is not valid,
     * or with status 500 (Internal Server Error) if the kanbanFlowBoard couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kanban-flow-boards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowBoard> updateKanbanFlowBoard(@RequestBody KanbanFlowBoard kanbanFlowBoard) throws URISyntaxException {
        log.debug("REST request to update KanbanFlowBoard : {}", kanbanFlowBoard);
        if (kanbanFlowBoard.getId() == null) {
            return createKanbanFlowBoard(kanbanFlowBoard);
        }
        KanbanFlowBoard result = kanbanFlowBoardRepository.save(kanbanFlowBoard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kanbanFlowBoard", kanbanFlowBoard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kanban-flow-boards : get all the kanbanFlowBoards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kanbanFlowBoards in body
     */
    @RequestMapping(value = "/kanban-flow-boards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KanbanFlowBoard> getAllKanbanFlowBoards() {
        log.debug("REST request to get all KanbanFlowBoards");
        List<KanbanFlowBoard> kanbanFlowBoards = kanbanFlowBoardRepository.findAll();
        return kanbanFlowBoards;
    }

    /**
     * GET  /kanban-flow-boards/:id : get the "id" kanbanFlowBoard.
     *
     * @param id the id of the kanbanFlowBoard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kanbanFlowBoard, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/kanban-flow-boards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowBoard> getKanbanFlowBoard(@PathVariable Long id) {
        log.debug("REST request to get KanbanFlowBoard : {}", id);
        KanbanFlowBoard kanbanFlowBoard = kanbanFlowBoardRepository.findOne(id);
        return Optional.ofNullable(kanbanFlowBoard)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kanban-flow-boards/:id : delete the "id" kanbanFlowBoard.
     *
     * @param id the id of the kanbanFlowBoard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/kanban-flow-boards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKanbanFlowBoard(@PathVariable Long id) {
        log.debug("REST request to delete KanbanFlowBoard : {}", id);
        kanbanFlowBoardRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kanbanFlowBoard", id.toString())).build();
    }
}
