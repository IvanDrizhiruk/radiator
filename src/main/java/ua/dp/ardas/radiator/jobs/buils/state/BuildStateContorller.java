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

@Component
public class BuildStateContorller {

	@Autowired
	private BuildStateExecutor executor;
	@Inject
	private BuildStateRepository buildStateRepository;
	@Inject
	private CommiterRepository commiterRepository;
//	@Autowired
//	private BuildStateDAO dao;
	@Inject
	private RadiatorProperties properties;
//	@Autowired
//	private SoundController soundController;
	@Value("${radiator.buildState.brokenSound}")
	private String pathToBrokenSound;

	public void execute() {

		//List<BuildState> lastData = Lists.newArrayList(dao.findLastData());

		for(BuildStateInstance instances : properties.buildState.instances) {
			BuildState buildState = executor.loadState(instances);

			//playSoundIfNeed(lastData, buildState);

			Optional<BuildState> res = buildStateRepository.findOneByStateAndLastRunTimestemp(buildState.getState(), buildState.getLastRunTimestemp());

			if(!res.isPresent()) {
				prepareCommiter(buildState);

				buildStateRepository.save(buildState);
			}
		}
	}

	private void prepareCommiter(BuildState buildState) {
		System.out.println("##################### " );
		System.out.println("##################### " );
		System.out.println("##################### " );
		System.out.println("##################### " );
		System.out.println("##################### " );
		Commiter commiter = buildState.getCommiter();
		if (null == commiter) {
			return;
		}
		Commiter dbcommiter = findOrCreate(commiter);
		buildState.setCommiter(dbcommiter);
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

//	private void playSoundIfNeed(List<BuildState> lastData, BuildState buildState) {
//		if(calculateIsNeedSound(buildState, lastData)) {
//			soundController.registerSoundPath(pathToBrokenSound);
//		}
//	}
//
//	private boolean calculateIsNeedSound(BuildState newBuildState, List<BuildState> lastData) {
//		if (null == newBuildState || States.SUCCESS == newBuildState.state) {
//			return false;
//		}
//
//		BuildState oldBuildState = findBuildStateByInstancesName(lastData, newBuildState.instancesName);
//
//		return null != oldBuildState
//				&& States.SUCCESS == oldBuildState.state;
//	}
//
//	private BuildState findBuildStateByInstancesName(List<BuildState> lastData, final String instancesName) {
//		try {
//			return find(lastData, new Predicate<BuildState> () {
//
//				@Override
//				public boolean apply(BuildState buildState) {
//					return instancesName.equals(buildState.instancesName);
//				}
//			});
//		} catch (Exception e) {
//			return null;
//		}
//	}
}
