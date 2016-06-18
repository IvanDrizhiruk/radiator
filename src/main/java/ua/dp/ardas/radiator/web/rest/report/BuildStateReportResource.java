package ua.dp.ardas.radiator.web.rest.report;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.ardas.radiator.domain.BuildState;
import ua.dp.ardas.radiator.repository.BuildStateRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for managing BuildState.
 */
@RestController
@RequestMapping("/api")
public class BuildStateReportResource {

    private final Logger log = LoggerFactory.getLogger(BuildStateReportResource.class);

    @Inject
    private BuildStateRepository buildStateRepository;


    @RequestMapping(value = "/report/build-states/last",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BuildState> getLastBuildStates() {
        log.debug("REST request to get last BuildStates");

        return buildStateRepository.findLastBuildStates();
    }
}
