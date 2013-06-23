package ua.dp.ardas.radiator.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import ua.dp.ardas.radiator.dto.hudson.api.Person;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class BuildStateUtils {

	private static final Joiner WORDS_JOINER = Joiner.on(", ").skipNulls();

	public static String calculateFailedEmail(List<Person> culprits) {
		if (null == culprits) {
			return null;
		}

		List<String> wordsList = Lists.transform(culprits,new Function<Person, String>() {
			public String apply(Person person) {
				return (null == person
						? null
						: person.fullName);
			}
		});

		return WORDS_JOINER.join(wordsList);
	}

	public static String calculateFailedName(List<Person> culprits) {
		if (null == culprits) {
			return null;
		}
		
        List<String> wordsList = Lists.transform(culprits, new Function<Person, String>() {
			public String apply(Person person) {
				if (null == person) {
					return null;
				}
				
				return WordUtils.capitalize(
						CharMatcher.is(' ').replaceFrom(
								StringUtils.defaultString(person.fullName), " "));
			}
		});
	            
        return WORDS_JOINER.join(wordsList);
	}
}
