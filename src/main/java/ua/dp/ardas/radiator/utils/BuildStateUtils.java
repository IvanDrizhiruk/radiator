package ua.dp.ardas.radiator.utils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang.WordUtils;
import ua.dp.ardas.radiator.domain.Commiter;
import ua.dp.ardas.radiator.dto.hudson.api.Person;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isBlank;

public class BuildStateUtils {

	private static final Joiner WORDS_JOINER = Joiner.on(", ").skipNulls();

	public static String calculateFailedEmail(List<Person> culprits, final String emailFormat) {
		if (null == culprits) {
			return EMPTY;
		}

		List<String> wordsList = Lists.transform(culprits,new Function<Person, String>() {
			public String apply(Person person) {
				return personFullNameToEmail(emailFormat, person);
			}

		});

		return WORDS_JOINER.join(wordsList);
	}
	
	private static String personFullNameToEmail(final String emailFormat, Person person) {
		return null == person || isBlank(person.fullName)
				? null
						: format(emailFormat, person.fullName);
	}

	public static String calculateFailedName(List<Person> culprits) {
		if (null == culprits) {
			return EMPTY;
		}
		
        List<String> wordsList = Lists.transform(culprits, new Function<Person, String>() {
			public String apply(Person person) {
				return personFullNameToName(person);
			}
		});
	            
        return WORDS_JOINER.join(wordsList);
	}
	
	private static String personFullNameToName(Person person) {
		if (null == person || isBlank(person.fullName)) {
			return null;
		}
		
		return WordUtils.capitalize(
				CharMatcher.is('.').replaceFrom(person.fullName, " "));
	}

	private static Commiter transformToCommiter(String emailFormat, Person person) {
		String name = personFullNameToName(person);
		String email = personFullNameToEmail(emailFormat, person);

		if (null == name || null == email) {
			return null;
		}

		Commiter commiter = new Commiter();
		commiter.setName(name);
		commiter.setEmail(email);

		return commiter;
	}

	public static List<Commiter> calculateCommiters(List<Person> culprits, final String emailFormat) {
		return culprits.stream()
				.map(person -> transformToCommiter(emailFormat, person))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
