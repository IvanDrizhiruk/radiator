package ua.dp.ardas.radiator.dao;

import java.util.List;

import ua.dp.ardas.radiator.dto.buils.state.BuildState;

public interface BuildStateDAO {
	void insert(BuildState buildState);
	List<BuildState> findAll();
	List<BuildState> findLastData();
}
