package ua.dp.ardas.radiator.jobs.buils.state;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import org.junit.Test;

import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.dto.buils.state.BuildState.States;
import ua.dp.ardas.radiator.dto.hudson.api.BuildDetails;
import ua.dp.ardas.radiator.resr.client.BuildStatusRestClient;

public class BuildStateExecutorTest {

	@Test
	public void shoudgenerateSuxesBuildIfNoErrors() {
		//given
		BuildStatusRestClient restClientMock = newRestClientMock(100, 100, 95, null);
		BuildStateExecutor executor = newBuildStateExecutor(restClientMock);
		BuildState expected = new BuildState(States.SUCCESS, BuildStateInstances.UI);
		//whent
		BuildState actual = executor.calculateState(BuildStateInstances.UI, "some url");
		//then
		assertReflectionEquals(expected, actual);
		
		verifyCallNumber(restClientMock, 1, 1, 1, 0);
	}


	private void verifyCallNumber(
			BuildStatusRestClient restClientMock,
			Integer lastBuildNumber,
			Integer lastSuccessfulBuildNumber,
			Integer lastFailedBuildNumber,
			Integer loadDetailsBuildNumber) {
		verify(restClientMock, times(lastBuildNumber)).loadLastBuildNumber(anyString());
		verify(restClientMock, times(lastSuccessfulBuildNumber)).loadLastSuccessfulBuildNumber(anyString());
		verify(restClientMock, times(lastFailedBuildNumber)).loadLastFailedBuildNumber(anyString());
		verify(restClientMock, times(loadDetailsBuildNumber)).loadBuildDetails(anyString(), anyInt());
	}


	private BuildStatusRestClient newRestClientMock(
			Integer lastBuildNumber,
			Integer lastSuccessfulBuildNumber,
			Integer lastFailedBuildNumber,
			Integer loadDetailsBuildNumber) {
		BuildStatusRestClient restClientMock = mock(BuildStatusRestClient.class);
		
		when(restClientMock.loadLastBuildNumber(anyString())).thenReturn(lastBuildNumber);
		when(restClientMock.loadLastSuccessfulBuildNumber(anyString())).thenReturn(lastSuccessfulBuildNumber);
		when(restClientMock.loadLastFailedBuildNumber(anyString())).thenReturn(lastFailedBuildNumber);
		when(restClientMock.loadBuildDetails(anyString(), anyInt())).thenReturn(loadBuildDetails(loadDetailsBuildNumber));
		
		return restClientMock;
	}


	private BuildDetails loadBuildDetails(Integer loadDetailsBuildNumber) {
		if (null == loadDetailsBuildNumber) {
			return null;
		}
		//LoadFromFile
		return new BuildDetails();
	}


	private BuildStateExecutor newBuildStateExecutor(final BuildStatusRestClient restClientMock) {
		return new BuildStateExecutor() {
			{
				this.restClient = restClientMock;
			}
			
		};
	}
}
