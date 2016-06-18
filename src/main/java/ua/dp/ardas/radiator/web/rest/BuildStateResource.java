package ua.dp.ardas.radiator.web.rest;

import com.codahale.metrics.annotation.Timed;
import ua.dp.ardas.radiator.domain.BuildState;
import ua.dp.ardas.radiator.repository.BuildStateRepository;
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
 * REST controller for managing BuildState.
 */
@RestController
@RequestMapping("/api")
public class BuildStateResource {

    private final Logger log = LoggerFactory.getLogger(BuildStateResource.class);
        
    @Inject
    private BuildStateRepository buildStateRepository;
    
    /**
     * POST  /build-states : Create a new buildState.
     *
     * @param buildState the buildState to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildState, or with status 400 (Bad Request) if the buildState has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/build-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildState> createBuildState(@RequestBody BuildState buildState) throws URISyntaxException {
        log.debug("REST request to save BuildState : {}", buildState);
        if (buildState.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("buildState", "idexists", "A new buildState cannot already have an ID")).body(null);
        }
        BuildState result = buildStateRepository.save(buildState);
        return ResponseEntity.created(new URI("/api/build-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("buildState", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /build-states : Updates an existing buildState.
     *
     * @param buildState the buildState to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildState,
     * or with status 400 (Bad Request) if the buildState is not valid,
     * or with status 500 (Internal Server Error) if the buildState couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/build-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildState> updateBuildState(@RequestBody BuildState buildState) throws URISyntaxException {
        log.debug("REST request to update BuildState : {}", buildState);
        if (buildState.getId() == null) {
            return createBuildState(buildState);
        }
        BuildState result = buildStateRepository.save(buildState);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("buildState", buildState.getId().toString()))
            .body(result);
    }

    /**
     * GET  /build-states : get all the buildStates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildStates in body
     */
    @RequestMapping(value = "/build-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BuildState> getAllBuildStates() {
        log.debug("REST request to get all BuildStates");
        List<BuildState> buildStates = buildStateRepository.findAll();
        return buildStates;
    }

    /**
     * GET  /build-states/:id : get the "id" buildState.
     *
     * @param id the id of the buildState to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildState, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/build-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildState> getBuildState(@PathVariable Long id) {
        log.debug("REST request to get BuildState : {}", id);
        BuildState buildState = buildStateRepository.findOne(id);
        return Optional.ofNullable(buildState)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /build-states/:id : delete the "id" buildState.
     *
     * @param id the id of the buildState to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/build-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBuildState(@PathVariable Long id) {
        log.debug("REST request to delete BuildState : {}", id);
        buildStateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("buildState", id.toString())).build();
    }

}
