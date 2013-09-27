package ua.dp.ardas.radiator.jobs.buils.state;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.fest.assertions.Assertions.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import ua.dp.ardas.radiator.dto.buils.state.Commiter;
import ua.dp.ardas.radiator.dto.hudson.api.Person;
import ua.dp.ardas.radiator.utils.BuildStateUtils;

public class BuildStateUtilsTest {

	@Test
	public void shuldReturnEmptyEmailsStringIfListEmpty() {
		//given
		List<Person> nullPersons = null;
		List<Person> emptyPersons = newArrayList();
		//when
		String nullEmails = BuildStateUtils.calculateFailedEmail(nullPersons, "%s@ardas.dp.ua");
		String emptyEmails = BuildStateUtils.calculateFailedEmail(emptyPersons, "%s@ardas.dp.ua");
		//then
		assertThat(nullEmails).isEqualTo(EMPTY);
		assertThat(emptyEmails).isEqualTo(EMPTY);
	}
	
	@Test
	public void shuldExtractOneEmailToString() {
		//given
		List<Person> culprits = newPersonList("ivan.drizhiruk");
		//when
		String emails = BuildStateUtils.calculateFailedEmail(culprits, "%s@ardas.dp.ua");
		//then
		assertThat(emails).isEqualTo("ivan.drizhiruk@ardas.dp.ua");
	}
	
	@Test
	public void shuldExtractManyEmailToStringAndMoveEmptynames() {
		//given
		List<Person> culprits = newPersonList("ivan.drizhiruk", null, EMPTY, " ", "nadya.drizhiruk");
		//when
		String emails = BuildStateUtils.calculateFailedEmail(culprits, "%s@ardas.dp.ua");
		//then
		assertThat(emails).isEqualTo("ivan.drizhiruk@ardas.dp.ua, nadya.drizhiruk@ardas.dp.ua");
	}
	
	@Test
	public void shuldreturnEmptyNamesStringIfListEmpty() {
		//given
		List<Person> nullPersons = null;
		List<Person> emptyPersons = newArrayList();
		//when
		String nullEmails = BuildStateUtils.calculateFailedName(nullPersons);
		String emptyEmails = BuildStateUtils.calculateFailedName(emptyPersons);
		//then
		assertThat(nullEmails).isEqualTo(EMPTY);
		assertThat(emptyEmails).isEqualTo(EMPTY);
	}
	
	@Test
	public void shuldExtractOneNameToString() {
		//given
		List<Person> culprits = newPersonList("ivan.drizhiruk");
		//when
		String emails = BuildStateUtils.calculateFailedName(culprits);
		//then
		assertThat(emails).isEqualTo("Ivan Drizhiruk");
	}
	
	@Test
	public void shuldExtractManyNamesToStringAndMoveEmptynames() {
		//given
		List<Person> culprits = newPersonList("ivan.drizhiruk", null, EMPTY, " ", "nadya.drizhiruk");
		//when
		String emails = BuildStateUtils.calculateFailedName(culprits);
		//then
		assertThat(emails).isEqualTo("Ivan Drizhiruk, Nadya Drizhiruk");
	}
	
	@Test
	public void shuldTransformAllPersonsToListCommiters() {
		//given
		List<Person> culprits = newPersonList("ivan.drizhiruk", null, EMPTY, " ", "nadya.drizhiruk");
		List<Commiter> expected = Arrays.asList(
				new Commiter("Ivan Drizhiruk", "ivan.drizhiruk@ardas.dp.ua"),
				new Commiter("Nadya Drizhiruk", "nadya.drizhiruk@ardas.dp.ua"));
		//when
		List<Commiter> actual = BuildStateUtils.calculateCommiters(culprits, "%s@ardas.dp.ua");
		//then
		
		assertReflectionEquals(expected, actual,ReflectionComparatorMode.LENIENT_ORDER);
	}
	
	
	
	private List<Person> newPersonList(String... emails) {
		ArrayList<Person> persons = newArrayList();
		
		for (String email : emails) {
			persons.add(new Person(format("http://%s", email), email));
		}
		
		return persons;
	}
}
