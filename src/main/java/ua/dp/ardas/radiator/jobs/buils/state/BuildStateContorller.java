package ua.dp.ardas.radiator.jobs.buils.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.dto.buils.state.BuildState;

@Component
public class BuildStateContorller {

	@Autowired
	private BuildStateExecutor executor;
	@Autowired
	private BuildStateDAO dao;
	@Autowired
	private BuildStateInstances buildStateInstances;
			
	public void execute() {
		for(BuildStateInstance instances : buildStateInstances.values()) {
			BuildState buildState = executor.loadState(instances);
			dao.insert(buildState);
		}
	}
}
