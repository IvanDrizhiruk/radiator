package ua.dp.ardas.radiator.dao.impl;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.jobs.buils.state.BuildStateInstances;

@Component
@Scope("singleton")
public class BuildStateDAOInMemory implements BuildStateDAO {

	private List<BuildState> buildStates = Lists.newArrayList();

	@Override
	public void insert(BuildState buildState) {
		//TODO ISD if not same with last (build id could be used.)
		moveOldStatisticBuInstance(buildState.instances);

		buildStates.add(buildState);
	}

	private void moveOldStatisticBuInstance(final BuildStateInstances instances) {
		Iterables.removeIf(buildStates, new Predicate<BuildState>() {

			@Override
			public boolean apply(BuildState state) {
				return state.instances == instances;
			}
			
		});
	}
	
	@Override
	public List<BuildState> findAll() {
		throw new NotImplementedException();
	}

	@Override
	public List<BuildState> findLastData() {
		return buildStates;
	}
}
