package ua.dp.ardas.radiator.resr.client.kanbanflow;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class KanbanFlowAuthInterceptor implements ClientHttpRequestInterceptor {

    private String token;

    public KanbanFlowAuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();

        String authToken = new String(Base64.encodeBase64(("apiToken:" + token).getBytes()));
        headers.add("Authorization", "Basic " + authToken);

        return execution.execute(request, body);
    }
}
