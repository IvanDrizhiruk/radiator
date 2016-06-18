package ua.dp.ardas.radiator.restclient;

import com.google.common.base.Preconditions;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

public class BasicAutorisationRestClient extends RestTemplate {

    public BasicAutorisationRestClient(String username, String password) {
        Preconditions.checkNotNull(username);

        setRequestFactory(new InterceptingClientHttpRequestFactory(getRequestFactory(),
                Arrays.asList(new BasicAuthorizationInterceptor(username, password))));

    }

    private static class BasicAuthorizationInterceptor implements ClientHttpRequestInterceptor {

        private final String username;
        private final String password;

        public BasicAuthorizationInterceptor(String username, String password) {
            this.username = username;
            this.password = (password == null ? "" : password);
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            byte[] token = Base64.getEncoder().encode((this.username + ":" + this.password).getBytes());
            request.getHeaders().add("Authorization", "Basic " + new String(token));

            return execution.execute(request, body);
        }
    }
}
