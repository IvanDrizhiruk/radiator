package ua.dp.ardas.radiator.jobs.buils.state;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ua.dp.ardas.radiator.config.AppConfig;

public class BuildStateInstancesTest {

	@Test
	public void shouldReturnAllAvailableInstansesAndItsParameters() {
		//given
		BuildStateInstances instances = newBuildStateInstances();
		List<BuildStateInstance> expected = Arrays.asList(
				new BuildStateInstance("UI", false, "ui", "Error in ui"),
				new BuildStateInstance("WS", false, "ws", "Error in ws"),
				new BuildStateInstance("UI_THUCYDIDES_TESTS1", true, "ui_thucydides_tests1", "Build has been broken"),
				new BuildStateInstance("UI_THUCYDIDES_TESTS2", true, "ui_thucydides_tests2", "Build has been broken"),
				new BuildStateInstance("UI_THUCYDIDES_TESTS3", false,"ui_thucydides_tests3", "Error in ui_thucydides_tests3"),
				new BuildStateInstance("UI_THUCYDIDES_TESTS4", true, "ui_thucydides_tests4", "Error in ui_thucydides_tests4"));
		
		//when
		List<BuildStateInstance> actual = instances.values();
		
		//then
		assertReflectionEquals(expected, actual);
	}

	private BuildStateInstances newBuildStateInstances() {
		return new BuildStateInstances() {
			{
				this.instancesString = "UI,WS,UI_THUCYDIDES_TESTS1,UI_THUCYDIDES_TESTS2,UI_THUCYDIDES_TESTS3,UI_THUCYDIDES_TESTS4";
				this.defaultErrorMessage = "Build has been broken";
				this.config = mock(AppConfig.class);
				
				String key = null;
				when(config.boolenadProperty("build.state.UI.configuration.issue")).thenReturn(false);
				when(config.boolenadProperty("build.state.UI_THUCYDIDES_TESTS1.configuration.issue")).thenReturn(true);
				when(config.boolenadProperty("build.state.UI_THUCYDIDES_TESTS2.configuration.issue")).thenReturn(true);
				when(config.boolenadProperty("build.state.UI_THUCYDIDES_TESTS3.configuration.issue")).thenReturn(false);
				when(config.boolenadProperty("build.state.UI_THUCYDIDES_TESTS4.configuration.issue")).thenReturn(true);
				
				when(config.stringProperty("build.state.UI.url")).thenReturn("ui");
				when(config.stringProperty("build.state.WS.url")).thenReturn("ws");
				when(config.stringProperty("build.state.UI_THUCYDIDES_TESTS1.url")).thenReturn("ui_thucydides_tests1");
				when(config.stringProperty("build.state.UI_THUCYDIDES_TESTS2.url")).thenReturn("ui_thucydides_tests2");
				when(config.stringProperty("build.state.UI_THUCYDIDES_TESTS3.url")).thenReturn("ui_thucydides_tests3");
				when(config.stringProperty("build.state.UI_THUCYDIDES_TESTS4.url")).thenReturn("ui_thucydides_tests4");
				
				when(config.stringProperty("build.state.UI.errorMessage")).thenReturn("Error in ui");
				when(config.stringProperty("build.state.WS.errorMessage")).thenReturn("Error in ws");
				when(config.stringProperty("build.state.UI_THUCYDIDES_TESTS3.errorMessage")).thenReturn("Error in ui_thucydides_tests3");
				when(config.stringProperty("build.state.UI_THUCYDIDES_TESTS4.errorMessage")).thenReturn("Error in ui_thucydides_tests4");

				config.boolenadProperty(key);
			}
		};
	}
}
