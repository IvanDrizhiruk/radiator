package ua.dp.ardas.radiator.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.jobs.buils.state.BuildState;

import com.google.common.collect.Lists;

@Component
@Scope("singleton")
public class BuildStateDAOInMemoryImpl implements BuildStateDAO {

	private List<BuildState> buildStates = Lists.newArrayList();

	@Override
	public void insert(BuildState buildState) {
		//TODO ISD if not same with last (build id could be used.)
		buildStates.add(buildState);
	}
	
	@Override
	public List<BuildState> findAll() {
		return buildStates;
	}
}