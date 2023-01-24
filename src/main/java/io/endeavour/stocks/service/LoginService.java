package io.endeavour.stocks.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
    private final String loginUrl;

    private final RestTemplate restTemplate;
    public LoginService(
            @Value("${client.stock-calculations.url}") String baseUrl,
            @Value("${client.login.username}") String username,
            @Value("${client.login.password}") String password){
        loginUrl = baseUrl + "/login";
        restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
    }

    public String getBearerToken(){
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, null, String.class);
        return response.getBody();
    }

}
