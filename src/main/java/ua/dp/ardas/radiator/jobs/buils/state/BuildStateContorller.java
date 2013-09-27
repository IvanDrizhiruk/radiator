package ua.dp.ardas.radiator.jobs.buils.state;

import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;

import ua.dp.ardas.radiator.dao.BuildStateDAO;
import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.dto.buils.state.BuildState.States;
import ua.dp.ardas.radiator.jobs.play.sound.SoundController;

@Component
public class BuildStateContorller {

	@Autowired
	private BuildStateExecutor executor;
	@Autowired
	private BuildStateDAO dao;
	@Autowired
	private BuildStateInstances buildStateInstances;
	@Autowired
	private SoundController soundController;
	@Value("${build.state.broken.sound}")
	private String pathToBrokenSound;
			
	public void execute() {
		List<BuildState> lastData = newArrayList(dao.findLastData());
		
		for(BuildStateInstance instances : buildStateInstances.values()) {
			BuildState buildState = executor.loadState(instances);

			playSoundIfNeed(lastData, buildState);
			
			dao.insert(buildState);
		}
	}

	private void playSoundIfNeed(List<BuildState> lastData, BuildState buildState) {
		if(calculateIsNeedSound(buildState, lastData)) {
			soundController.registerSoundPath(pathToBrokenSound);
		}
	}

	private boolean calculateIsNeedSound(BuildState newBuildState, List<BuildState> lastData) {
		if (States.SUCCESS == newBuildState.state) {
			return false;
		}
			
		BuildState oldBuildState = findBuildStateByInstancesName(lastData, newBuildState.instancesName);
		
		return null != oldBuildState
				&& States.SUCCESS == oldBuildState.state;
	}

	private BuildState findBuildStateByInstancesName(List<BuildState> lastData, final String instancesName) {
		try {
			return find(lastData, new Predicate<BuildState> () {
	
				@Override
				public boolean apply(BuildState buildState) {
					return instancesName.equals(buildState.instancesName);
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
}
