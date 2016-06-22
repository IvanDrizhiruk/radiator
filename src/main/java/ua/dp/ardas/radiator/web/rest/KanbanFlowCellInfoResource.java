package ua.dp.ardas.radiator.web.rest;

import com.codahale.metrics.annotation.Timed;
import ua.dp.ardas.radiator.domain.KanbanFlowCellInfo;
import ua.dp.ardas.radiator.repository.KanbanFlowCellInfoRepository;
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
 * REST controller for managing KanbanFlowCellInfo.
 */
@RestController
@RequestMapping("/api")
public class KanbanFlowCellInfoResource {

    private final Logger log = LoggerFactory.getLogger(KanbanFlowCellInfoResource.class);
        
    @Inject
    private KanbanFlowCellInfoRepository kanbanFlowCellInfoRepository;
    
    /**
     * POST  /kanban-flow-cell-infos : Create a new kanbanFlowCellInfo.
     *
     * @param kanbanFlowCellInfo the kanbanFlowCellInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kanbanFlowCellInfo, or with status 400 (Bad Request) if the kanbanFlowCellInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kanban-flow-cell-infos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowCellInfo> createKanbanFlowCellInfo(@RequestBody KanbanFlowCellInfo kanbanFlowCellInfo) throws URISyntaxException {
        log.debug("REST request to save KanbanFlowCellInfo : {}", kanbanFlowCellInfo);
        if (kanbanFlowCellInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kanbanFlowCellInfo", "idexists", "A new kanbanFlowCellInfo cannot already have an ID")).body(null);
        }
        KanbanFlowCellInfo result = kanbanFlowCellInfoRepository.save(kanbanFlowCellInfo);
        return ResponseEntity.created(new URI("/api/kanban-flow-cell-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kanbanFlowCellInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kanban-flow-cell-infos : Updates an existing kanbanFlowCellInfo.
     *
     * @param kanbanFlowCellInfo the kanbanFlowCellInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kanbanFlowCellInfo,
     * or with status 400 (Bad Request) if the kanbanFlowCellInfo is not valid,
     * or with status 500 (Internal Server Error) if the kanbanFlowCellInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kanban-flow-cell-infos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowCellInfo> updateKanbanFlowCellInfo(@RequestBody KanbanFlowCellInfo kanbanFlowCellInfo) throws URISyntaxException {
        log.debug("REST request to update KanbanFlowCellInfo : {}", kanbanFlowCellInfo);
        if (kanbanFlowCellInfo.getId() == null) {
            return createKanbanFlowCellInfo(kanbanFlowCellInfo);
        }
        KanbanFlowCellInfo result = kanbanFlowCellInfoRepository.save(kanbanFlowCellInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kanbanFlowCellInfo", kanbanFlowCellInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kanban-flow-cell-infos : get all the kanbanFlowCellInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kanbanFlowCellInfos in body
     */
    @RequestMapping(value = "/kanban-flow-cell-infos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KanbanFlowCellInfo> getAllKanbanFlowCellInfos() {
        log.debug("REST request to get all KanbanFlowCellInfos");
        List<KanbanFlowCellInfo> kanbanFlowCellInfos = kanbanFlowCellInfoRepository.findAll();
        return kanbanFlowCellInfos;
    }

    /**
     * GET  /kanban-flow-cell-infos/:id : get the "id" kanbanFlowCellInfo.
     *
     * @param id the id of the kanbanFlowCellInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kanbanFlowCellInfo, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/kanban-flow-cell-infos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KanbanFlowCellInfo> getKanbanFlowCellInfo(@PathVariable Long id) {
        log.debug("REST request to get KanbanFlowCellInfo : {}", id);
        KanbanFlowCellInfo kanbanFlowCellInfo = kanbanFlowCellInfoRepository.findOne(id);
        return Optional.ofNullable(kanbanFlowCellInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kanban-flow-cell-infos/:id : delete the "id" kanbanFlowCellInfo.
     *
     * @param id the id of the kanbanFlowCellInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/kanban-flow-cell-infos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKanbanFlowCellInfo(@PathVariable Long id) {
        log.debug("REST request to delete KanbanFlowCellInfo : {}", id);
        kanbanFlowCellInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kanbanFlowCellInfo", id.toString())).build();
    }

}
