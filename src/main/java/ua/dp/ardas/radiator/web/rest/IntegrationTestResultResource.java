package ua.dp.ardas.radiator.web.rest;

import com.codahale.metrics.annotation.Timed;
import ua.dp.ardas.radiator.domain.IntegrationTestResult;
import ua.dp.ardas.radiator.repository.IntegrationTestResultRepository;
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
 * REST controller for managing IntegrationTestResult.
 */
@RestController
@RequestMapping("/api")
public class IntegrationTestResultResource {

    private final Logger log = LoggerFactory.getLogger(IntegrationTestResultResource.class);
        
    @Inject
    private IntegrationTestResultRepository integrationTestResultRepository;
    
    /**
     * POST  /integration-test-results : Create a new integrationTestResult.
     *
     * @param integrationTestResult the integrationTestResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new integrationTestResult, or with status 400 (Bad Request) if the integrationTestResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/integration-test-results",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IntegrationTestResult> createIntegrationTestResult(@RequestBody IntegrationTestResult integrationTestResult) throws URISyntaxException {
        log.debug("REST request to save IntegrationTestResult : {}", integrationTestResult);
        if (integrationTestResult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("integrationTestResult", "idexists", "A new integrationTestResult cannot already have an ID")).body(null);
        }
        IntegrationTestResult result = integrationTestResultRepository.save(integrationTestResult);
        return ResponseEntity.created(new URI("/api/integration-test-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("integrationTestResult", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /integration-test-results : Updates an existing integrationTestResult.
     *
     * @param integrationTestResult the integrationTestResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated integrationTestResult,
     * or with status 400 (Bad Request) if the integrationTestResult is not valid,
     * or with status 500 (Internal Server Error) if the integrationTestResult couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/integration-test-results",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IntegrationTestResult> updateIntegrationTestResult(@RequestBody IntegrationTestResult integrationTestResult) throws URISyntaxException {
        log.debug("REST request to update IntegrationTestResult : {}", integrationTestResult);
        if (integrationTestResult.getId() == null) {
            return createIntegrationTestResult(integrationTestResult);
        }
        IntegrationTestResult result = integrationTestResultRepository.save(integrationTestResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("integrationTestResult", integrationTestResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /integration-test-results : get all the integrationTestResults.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of integrationTestResults in body
     */
    @RequestMapping(value = "/integration-test-results",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<IntegrationTestResult> getAllIntegrationTestResults() {
        log.debug("REST request to get all IntegrationTestResults");
        List<IntegrationTestResult> integrationTestResults = integrationTestResultRepository.findAll();
        return integrationTestResults;
    }

    /**
     * GET  /integration-test-results/:id : get the "id" integrationTestResult.
     *
     * @param id the id of the integrationTestResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the integrationTestResult, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/integration-test-results/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IntegrationTestResult> getIntegrationTestResult(@PathVariable Long id) {
        log.debug("REST request to get IntegrationTestResult : {}", id);
        IntegrationTestResult integrationTestResult = integrationTestResultRepository.findOne(id);
        return Optional.ofNullable(integrationTestResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /integration-test-results/:id : delete the "id" integrationTestResult.
     *
     * @param id the id of the integrationTestResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/integration-test-results/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIntegrationTestResult(@PathVariable Long id) {
        log.debug("REST request to delete IntegrationTestResult : {}", id);
        integrationTestResultRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("integrationTestResult", id.toString())).build();
    }

}
