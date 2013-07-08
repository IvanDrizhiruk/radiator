package ua.dp.ardas.radiator.dao.impl;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.jobs.buils.state.BuildState;

@Component
@Scope("singleton")
public class BuildStateDAOInMemory implements BuildStateDAO {

	private List<BuildState> buildStates = Lists.newArrayList();

	@Override
	public void insert(final BuildState buildState) {
		//TODO ISD if not same with last (build id could be used.)
		
		buildStates = newArrayList(filter(buildStates, new Predicate<BuildState>() {

			@Override
			public boolean apply(BuildState input) {
				return input.instances == buildState.instances;
			}
			
		}));
		
		buildStates.add(buildState);
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