package ua.dp.ardas.radiator.jobs.buils.state;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static ua.dp.ardas.radiator.dto.buils.state.BuildState.States.BUILD_FAILED;
import static ua.dp.ardas.radiator.dto.buils.state.BuildState.States.CONFIGURATION_FAILED;
import static ua.dp.ardas.radiator.dto.buils.state.BuildState.States.SUCCESS;
import static ua.dp.ardas.radiator.jobs.buils.state.BuildStateInstances.UI;
import static ua.dp.ardas.radiator.jobs.buils.state.BuildStateInstances.UI_THUCYDIDES_TESTS1;
import static ua.dp.ardas.radiator.utils.ResourcesUtils.resourceAsString;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gson.reflect.TypeToken;

import ua.dp.ardas.radiator.config.AppConfig;
import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.dto.hudson.api.BuildDetails;
import ua.dp.ardas.radiator.resr.client.BuildStatusRestClient;
import ua.dp.ardas.radiator.utils.JsonUtils;

public class BuildStateExecutorTest {

	@Test
	public void shoudgenerateSuxesBuildIfNoErrors() {
		//given
		BuildStatusRestClient restClientMock = newRestClientMock(6275, 6275, 6270);
		BuildStateExecutor executor = newBuildStateExecutor(restClientMock);
		BuildState expected = new BuildState(SUCCESS, UI);
		//whent
		BuildState actual = executor.calculateState(UI, "some url");
		//then
		assertReflectionEquals(expected, actual);
		verifyCallNumber(restClientMock, 1, 1, 1, 0);
	}
	
	@Test
	public void shoudgenerateBuildFailledState() {
		//given
		BuildStatusRestClient restClientMock = newRestClientMock(6275, 6270, 6275);
		BuildStateExecutor executor = newBuildStateExecutor(restClientMock);
		BuildState expected = new BuildState(BUILD_FAILED, UI);
		expected.errorMessage = "job.UI.errorMessage";
		expected.failedEmail ="nick.foster@ardas.dp.ua";
		expected.failedName = "Nick Foster";
		//whent
		BuildState actual = executor.calculateState(UI, "some url");
		//then
		assertReflectionEquals(expected, actual);
		verifyCallNumber(restClientMock, 1, 1, 1, 1);
	}
	
	@Test
	public void shoudgenerateBuildConfigurationFailledStateIfConfigurationInstans() {
		//given
		BuildStatusRestClient restClientMock = newRestClientMock(6275, 6270, 6275);
		BuildStateExecutor executor = newBuildStateExecutor(restClientMock);
		BuildState expected = new BuildState(CONFIGURATION_FAILED, UI_THUCYDIDES_TESTS1);
		//whent
		BuildState actual = executor.calculateState(UI_THUCYDIDES_TESTS1, "some url");
		//then
		assertReflectionEquals(expected, actual);
		verifyCallNumber(restClientMock, 1, 1, 1, 0);
	}
	
	@Test
	public void shoudgenerateBuildConfigurationFailledStateIflastBuildInProgresOrAborted() {
		//given
		BuildStatusRestClient restClientMock = newRestClientMock(6298, 6295, 6297);
		BuildStateExecutor executor = newBuildStateExecutor(restClientMock);
		BuildState expected = new BuildState(CONFIGURATION_FAILED, UI);
		//whent
		BuildState actual = executor.calculateState(UI, "some url");
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
			Integer lastFailedBuildNumber) {
		BuildStatusRestClient restClientMock = mock(BuildStatusRestClient.class);
		
		when(restClientMock.loadLastBuildNumber(anyString())).thenReturn(lastBuildNumber);
		when(restClientMock.loadLastSuccessfulBuildNumber(anyString())).thenReturn(lastSuccessfulBuildNumber);
		when(restClientMock.loadLastFailedBuildNumber(anyString())).thenReturn(lastFailedBuildNumber);
		when(restClientMock.loadBuildDetails(anyString(), anyInt())).thenAnswer(
				new Answer<BuildDetails>() {

					@Override
					public BuildDetails answer(InvocationOnMock invocation) throws Throwable {
						Object[] args = invocation.getArguments();

						return loadBuildDetails((Integer)args[1]);
					}
				});
		
		return restClientMock;
	}


	private BuildDetails loadBuildDetails(Integer buildNumber) {
		String json = resourceAsString(String.format("buildStates/%d.json", buildNumber));
		
		return JsonUtils.fromJSON(json, new TypeToken<BuildDetails>(){}.getType());
	}


	private BuildStateExecutor newBuildStateExecutor(final BuildStatusRestClient restClientMock) {
		return new BuildStateExecutor() {
			{
				this.restClient = restClientMock;
				this.config = mock(AppConfig.class);
				when(this.config.stringProperty(anyString())).thenAnswer(
						new Answer<String>() {

							@Override
							public String answer(InvocationOnMock invocation) throws Throwable {
								return (String) invocation.getArguments()[0];
							}
						});
				this.emailFormat = "%s@ardas.dp.ua";
			}
		};
	}
}
