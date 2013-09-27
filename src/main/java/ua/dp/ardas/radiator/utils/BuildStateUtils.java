package ua.dp.ardas.radiator.utils;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.List;

import org.apache.commons.lang.WordUtils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import ua.dp.ardas.radiator.dto.buils.state.Commiter;
import ua.dp.ardas.radiator.dto.hudson.api.Person;

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

	public static List<Commiter> calculateCommiters(List<Person> culprits, final String emailFormat) {
		List<Commiter> commiters = Lists.transform(culprits,new Function<Person, Commiter>() {
			public Commiter apply(Person person) {
				
				String name = personFullNameToName(person);
				String email = personFullNameToEmail(emailFormat, person);
				
				return (null == name || null == email
						? null
						: new Commiter(name, email));
			}
		});
		
		return newArrayList(
				filter(commiters, notNull()));
	}
}
