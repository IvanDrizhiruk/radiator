package ua.dp.ardas.radiator.restclient.auth;

import java.util.Base64;

public class TokenAuthTokenGenerator implements AuthTokenGenerator {


    private String token;

    public TokenAuthTokenGenerator(String token) {
        this.token = token;
    }

    @Override
    public String generateToken() {
        byte[] token = Base64.getEncoder().encode(("apiToken:" + this.token).getBytes());


        return new String(token);
    }
}
