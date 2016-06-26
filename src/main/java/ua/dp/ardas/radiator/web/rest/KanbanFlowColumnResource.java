package ua.dp.ardas.radiator.web.rest;

import com.codahale.metrics.annotation.Timed;
import ua.dp.ardas.radiator.domain.KanbanFlowColumn;
import ua.dp.ardas.radiator.repository.KanbanFlowColumnRepository;
import ua.dp.ardas.radiator.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing KanbanFlowColumn.
 */
@RestController
@RequestMapping("/api")
public class KanbanFlowColumnResource {

    private final Logger log = LoggerFactory.getLogger(KanbanFlowColumnResource.class);
        
    @Inject
    private KanbanFlowColumnRepository kanbanFlowColumnRepository;
    
    /**
     * POST  /kanban-flow-columns : Create a new kanbanFlowColumn.
     *
     * @param kanbanFlowColumn the kanbanFlowColumn to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kanbanFlowColumn, or with status 400 (Bad Request) if the kanbanFlowColumn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kanban-flow-columns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowColumn> createKanbanFlowColumn(@RequestBody KanbanFlowColumn kanbanFlowColumn) throws URISyntaxException {
        log.debug("REST request to save KanbanFlowColumn : {}", kanbanFlowColumn);
        if (kanbanFlowColumn.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kanbanFlowColumn", "idexists", "A new kanbanFlowColumn cannot already have an ID")).body(null);
        }
        KanbanFlowColumn result = kanbanFlowColumnRepository.save(kanbanFlowColumn);
        return ResponseEntity.created(new URI("/api/kanban-flow-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kanbanFlowColumn", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kanban-flow-columns : Updates an existing kanbanFlowColumn.
     *
     * @param kanbanFlowColumn the kanbanFlowColumn to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kanbanFlowColumn,
     * or with status 400 (Bad Request) if the kanbanFlowColumn is not valid,
     * or with status 500 (Internal Server Error) if the kanbanFlowColumn couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kanban-flow-columns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowColumn> updateKanbanFlowColumn(@RequestBody KanbanFlowColumn kanbanFlowColumn) throws URISyntaxException {
        log.debug("REST request to update KanbanFlowColumn : {}", kanbanFlowColumn);
        if (kanbanFlowColumn.getId() == null) {
            return createKanbanFlowColumn(kanbanFlowColumn);
        }
        KanbanFlowColumn result = kanbanFlowColumnRepository.save(kanbanFlowColumn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kanbanFlowColumn", kanbanFlowColumn.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kanban-flow-columns : get all the kanbanFlowColumns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kanbanFlowColumns in body
     */
    @RequestMapping(value = "/kanban-flow-columns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KanbanFlowColumn> getAllKanbanFlowColumns() {
        log.debug("REST request to get all KanbanFlowColumns");
        List<KanbanFlowColumn> kanbanFlowColumns = kanbanFlowColumnRepository.findAll();
        return kanbanFlowColumns;
    }

    /**
     * GET  /kanban-flow-columns/:id : get the "id" kanbanFlowColumn.
     *
     * @param id the id of the kanbanFlowColumn to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kanbanFlowColumn, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/kanban-flow-columns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowColumn> getKanbanFlowColumn(@PathVariable Long id) {
        log.debug("REST request to get KanbanFlowColumn : {}", id);
        KanbanFlowColumn kanbanFlowColumn = kanbanFlowColumnRepository.findOne(id);
        return Optional.ofNullable(kanbanFlowColumn)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kanban-flow-columns/:id : delete the "id" kanbanFlowColumn.
     *
     * @param id the id of the kanbanFlowColumn to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/kanban-flow-columns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKanbanFlowColumn(@PathVariable Long id) {
        log.debug("REST request to delete KanbanFlowColumn : {}", id);
        kanbanFlowColumnRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kanbanFlowColumn", id.toString())).build();
    }

}
