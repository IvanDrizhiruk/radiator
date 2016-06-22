package ua.dp.ardas.radiator.web.rest;

import com.codahale.metrics.annotation.Timed;
import ua.dp.ardas.radiator.domain.KanbanFlowSwimlane;
import ua.dp.ardas.radiator.repository.KanbanFlowSwimlaneRepository;
import ua.dp.ardas.radiator.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing KanbanFlowSwimlane.
 */
@RestController
@RequestMapping("/api")
public class KanbanFlowSwimlaneResource {

    private final Logger log = LoggerFactory.getLogger(KanbanFlowSwimlaneResource.class);
        
    @Inject
    private KanbanFlowSwimlaneRepository kanbanFlowSwimlaneRepository;
    
    /**
     * POST  /kanban-flow-swimlanes : Create a new kanbanFlowSwimlane.
     *
     * @param kanbanFlowSwimlane the kanbanFlowSwimlane to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kanbanFlowSwimlane, or with status 400 (Bad Request) if the kanbanFlowSwimlane has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kanban-flow-swimlanes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowSwimlane> createKanbanFlowSwimlane(@RequestBody KanbanFlowSwimlane kanbanFlowSwimlane) throws URISyntaxException {
        log.debug("REST request to save KanbanFlowSwimlane : {}", kanbanFlowSwimlane);
        if (kanbanFlowSwimlane.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kanbanFlowSwimlane", "idexists", "A new kanbanFlowSwimlane cannot already have an ID")).body(null);
        }
        KanbanFlowSwimlane result = kanbanFlowSwimlaneRepository.save(kanbanFlowSwimlane);
        return ResponseEntity.created(new URI("/api/kanban-flow-swimlanes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kanbanFlowSwimlane", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kanban-flow-swimlanes : Updates an existing kanbanFlowSwimlane.
     *
     * @param kanbanFlowSwimlane the kanbanFlowSwimlane to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kanbanFlowSwimlane,
     * or with status 400 (Bad Request) if the kanbanFlowSwimlane is not valid,
     * or with status 500 (Internal Server Error) if the kanbanFlowSwimlane couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kanban-flow-swimlanes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowSwimlane> updateKanbanFlowSwimlane(@RequestBody KanbanFlowSwimlane kanbanFlowSwimlane) throws URISyntaxException {
        log.debug("REST request to update KanbanFlowSwimlane : {}", kanbanFlowSwimlane);
        if (kanbanFlowSwimlane.getId() == null) {
            return createKanbanFlowSwimlane(kanbanFlowSwimlane);
        }
        KanbanFlowSwimlane result = kanbanFlowSwimlaneRepository.save(kanbanFlowSwimlane);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kanbanFlowSwimlane", kanbanFlowSwimlane.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kanban-flow-swimlanes : get all the kanbanFlowSwimlanes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kanbanFlowSwimlanes in body
     */
    @RequestMapping(value = "/kanban-flow-swimlanes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KanbanFlowSwimlane> getAllKanbanFlowSwimlanes() {
        log.debug("REST request to get all KanbanFlowSwimlanes");
        List<KanbanFlowSwimlane> kanbanFlowSwimlanes = kanbanFlowSwimlaneRepository.findAll();
        return kanbanFlowSwimlanes;
    }

    /**
     * GET  /kanban-flow-swimlanes/:id : get the "id" kanbanFlowSwimlane.
     *
     * @param id the id of the kanbanFlowSwimlane to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kanbanFlowSwimlane, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/kanban-flow-swimlanes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowSwimlane> getKanbanFlowSwimlane(@PathVariable Long id) {
        log.debug("REST request to get KanbanFlowSwimlane : {}", id);
        KanbanFlowSwimlane kanbanFlowSwimlane = kanbanFlowSwimlaneRepository.findOne(id);
        return Optional.ofNullable(kanbanFlowSwimlane)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kanban-flow-swimlanes/:id : delete the "id" kanbanFlowSwimlane.
     *
     * @param id the id of the kanbanFlowSwimlane to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/kanban-flow-swimlanes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKanbanFlowSwimlane(@PathVariable Long id) {
        log.debug("REST request to delete KanbanFlowSwimlane : {}", id);
        kanbanFlowSwimlaneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kanbanFlowSwimlane", id.toString())).build();
    }

}
