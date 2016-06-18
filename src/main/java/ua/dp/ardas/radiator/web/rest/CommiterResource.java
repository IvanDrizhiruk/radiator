package ua.dp.ardas.radiator.web.rest;

import com.codahale.metrics.annotation.Timed;
import ua.dp.ardas.radiator.domain.Commiter;
import ua.dp.ardas.radiator.repository.CommiterRepository;
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
 * REST controller for managing Commiter.
 */
@RestController
@RequestMapping("/api")
public class CommiterResource {

    private final Logger log = LoggerFactory.getLogger(CommiterResource.class);
        
    @Inject
    private CommiterRepository commiterRepository;
    
    /**
     * POST  /commiters : Create a new commiter.
     *
     * @param commiter the commiter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commiter, or with status 400 (Bad Request) if the commiter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/commiters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Commiter> createCommiter(@RequestBody Commiter commiter) throws URISyntaxException {
        log.debug("REST request to save Commiter : {}", commiter);
        if (commiter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("commiter", "idexists", "A new commiter cannot already have an ID")).body(null);
        }
        Commiter result = commiterRepository.save(commiter);
        return ResponseEntity.created(new URI("/api/commiters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("commiter", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commiters : Updates an existing commiter.
     *
     * @param commiter the commiter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commiter,
     * or with status 400 (Bad Request) if the commiter is not valid,
     * or with status 500 (Internal Server Error) if the commiter couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/commiters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Commiter> updateCommiter(@RequestBody Commiter commiter) throws URISyntaxException {
        log.debug("REST request to update Commiter : {}", commiter);
        if (commiter.getId() == null) {
            return createCommiter(commiter);
        }
        Commiter result = commiterRepository.save(commiter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("commiter", commiter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commiters : get all the commiters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commiters in body
     */
    @RequestMapping(value = "/commiters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Commiter> getAllCommiters() {
        log.debug("REST request to get all Commiters");
        List<Commiter> commiters = commiterRepository.findAll();
        return commiters;
    }

    /**
     * GET  /commiters/:id : get the "id" commiter.
     *
     * @param id the id of the commiter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commiter, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/commiters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Commiter> getCommiter(@PathVariable Long id) {
        log.debug("REST request to get Commiter : {}", id);
        Commiter commiter = commiterRepository.findOne(id);
        return Optional.ofNullable(commiter)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /commiters/:id : delete the "id" commiter.
     *
     * @param id the id of the commiter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/commiters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCommiter(@PathVariable Long id) {
        log.debug("REST request to delete Commiter : {}", id);
        commiterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("commiter", id.toString())).build();
    }

}
