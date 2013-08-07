package ua.dp.ardas.radiator.config;

import static java.lang.Boolean.parseBoolean;
import static org.apache.commons.lang.StringUtils.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({"config.properties", "auth.properties"})
public class AppConfig {

	@Autowired
	private Environment environment;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	
	public String stringProperty(String key) {
		return environment.getProperty(key);
	}

	public Boolean boolenadProperty(String key) {
		String value = environment.getProperty(key);
		
		return isEmpty(value)
				? null
				: parseBoolean(value);
	}
}
