package ua.dp.ardas.radiator.resr.client;

import javax.annotation.PostConstruct;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.client.RestTemplate;

//@Component
public class RestClient {
    
    private final RestTemplate template;
    private final RestClientProperties clientProperties;
    private final DefaultHttpClient httpClient;

    //@Autowired
    public RestClient(RestTemplate template, RestClientProperties clientProperties,
                      DefaultHttpClient httpClient) {
        this.template = template;
        this.clientProperties = clientProperties;
        this.httpClient = httpClient;
    }

    @PostConstruct
    public void init() {
        setCredentials(clientProperties.username, clientProperties.password);
    }

    /**
     * Gets rest template.
     */
    public RestTemplate getRestTemplate() {
        return template;
    }

    /**
     * Creates URL based on the URI passed in.
     */
    public String createUrl(String uri) {
        StringBuilder sb = new StringBuilder();

        sb.append(clientProperties.url);
        sb.append(clientProperties.apiPath);
        sb.append(uri);
        
        System.out.println("URL is '" + sb.toString() + "'.");
        
        return sb.toString();
    }
    
    /**
     * Set default credentials on HTTP client.
     */
    public void setCredentials(String userName, String password) {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(clientProperties.username, clientProperties.password);
        AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        
        httpClient.getCredentialsProvider().setCredentials(authScope, creds);
    }
}
   