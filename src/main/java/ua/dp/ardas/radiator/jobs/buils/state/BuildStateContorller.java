package ua.dp.ardas.radiator.jobs.buils.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dp.ardas.radiator.config.RadiatorProperties;
import ua.dp.ardas.radiator.domain.BuildState;
import ua.dp.ardas.radiator.domain.Commiter;
import ua.dp.ardas.radiator.repository.BuildStateRepository;
import ua.dp.ardas.radiator.repository.CommiterRepository;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BuildStateContorller {

	@Autowired
	private BuildStateExecutor executor;
	@Inject
	private BuildStateRepository buildStateRepository;
	@Inject
	private CommiterRepository commiterRepository;
	@Inject
	private RadiatorProperties properties;
	@Value("${radiator.buildState.brokenSound}")
	private String pathToBrokenSound;

	public void execute() {

		for(BuildStateInstance instances : properties.buildState.instances) {
			BuildState buildState = executor.loadState(instances);

			Optional<BuildState> res = Optional.empty();

			if (null == buildState || null ==  buildState.getLastRunTimestemp()) {
				buildStateRepository.findOneByStateAndLastRunTimestemp(buildState.getState(), buildState.getLastRunTimestemp());
			}

			if(!res.isPresent()) {
				prepareCommiters(buildState);

				buildStateRepository.save(buildState);
			}
		}
	}

	private void prepareCommiters(BuildState buildState) {
		Set<Commiter> commiters = buildState.getCommiters();
		if (null == commiters) {
			return;
		}

		Set<Commiter> updatedCommiters = commiters.stream()
				.map(this::findOrCreate)
				.collect(Collectors.toSet());

		buildState.setCommiters(updatedCommiters);
	}

	public Commiter findOrCreate(Commiter commiter) {
		if (null == commiter) {
			return null;
		}

		Commiter dbCommiter = commiterRepository.findOneByEmail(commiter.getEmail());

		return null == dbCommiter
				? commiterRepository.save(commiter)
				: dbCommiter;
	}
}
