package ua.dp.ardas.radiator.web.rest.report;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.ardas.radiator.domain.IntegrationTestResult;
import ua.dp.ardas.radiator.repository.IntegrationTestResultRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for managing BuildState.
 */
@RestController
@RequestMapping("/api")
public class IntegrationTestsReportResource {

    private final Logger log = LoggerFactory.getLogger(IntegrationTestsReportResource.class);

    @Inject
    private IntegrationTestResultRepository integrationTestResultRepository;


    @RequestMapping(value = "/report/integration-test/last",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<IntegrationTestResult> getLastIntegratinTests() {
        log.debug("REST request to get last BuildStates");

        return integrationTestResultRepository.findLasts();
    }
}
