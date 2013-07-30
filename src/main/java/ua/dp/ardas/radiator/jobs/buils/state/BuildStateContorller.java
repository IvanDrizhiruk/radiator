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
			
	public void execute() {
		for(BuildStateInstances instances : BuildStateInstances.values()) {
			BuildState buildState = executor.loadState(instances);
			dao.insert(buildState);
		}
	}
}
