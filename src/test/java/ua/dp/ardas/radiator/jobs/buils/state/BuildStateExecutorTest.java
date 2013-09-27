package ua.dp.ardas.radiator.jobs.buils.state;

import static com.google.common.collect.Lists.newArrayList;
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
import static ua.dp.ardas.radiator.utils.ResourcesUtils.resourceAsString;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gson.reflect.TypeToken;

import ua.dp.ardas.radiator.config.AppConfig;
import ua.dp.ardas.radiator.dto.buils.state.BuildState;
import ua.dp.ardas.radiator.dto.buils.state.Commiter;
import ua.dp.ardas.radiator.dto.hudson.api.BuildDetails;
import ua.dp.ardas.radiator.resr.client.BuildStatusRestClient;
import ua.dp.ardas.radiator.utils.JsonUtils;

public class BuildStateExecutorTest {

	private static final BuildStateInstance UI = new BuildStateInstance("UI", false, "ui", "Error in ui");
	private static final BuildStateInstance UI_THUCYDIDES_TESTS1 = new BuildStateInstance("UI_THUCYDIDES_TESTS1", true, "ui_thucydides_tests1", "Error in ui_thucydides_tests1");;


	@Test
	public void shoudgenerateSuxesBuildIfNoErrors() {
		//given
		BuildStatusRestClient restClientMock = newRestClientMock(6275, 6275, 6270);
		BuildStateExecutor executor = newBuildStateExecutor(restClientMock);
		BuildState expected = new BuildState(SUCCESS, UI.name);
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
		BuildState expected = new BuildState(BUILD_FAILED, UI.name);
		expected.errorMessage = UI.errorMessage;
		expected.commiters = newArrayList(new Commiter("Nick Foster", "nick.foster@ardas.dp.ua"));

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
		BuildState expected = new BuildState(CONFIGURATION_FAILED, UI_THUCYDIDES_TESTS1.name);
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
		BuildState expected = new BuildState(CONFIGURATION_FAILED, UI.name);
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
