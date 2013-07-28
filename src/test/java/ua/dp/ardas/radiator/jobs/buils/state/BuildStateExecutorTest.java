package ua.dp.ardas.radiator.jobs.buils.state;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import org.junit.Ignore;
import org.junit.Test;

import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.dto.buils.state.BuildState.States;

public class BuildStateExecutorTest {

	@Test
	@Ignore
	public void shoudgenerateSuxesBuildIfNoErrors() {
		//given
		BuildStateExecutor executor = new BuildStateExecutorTestExt();
		BuildState expected = new BuildState(States.SUCCESS, BuildStateInstances.UI);
		//whent
		BuildStateInstances instances = null;
		String url = null;
		BuildState actual = executor.calculateState(instances, url);
		//then
		assertReflectionEquals(expected, actual);
	}
	
	
	class BuildStateExecutorTestExt extends BuildStateExecutor {
		
	}

}
