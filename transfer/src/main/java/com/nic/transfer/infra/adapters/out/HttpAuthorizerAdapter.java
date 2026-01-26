package com.nic.transfer.infra.adapters.out;

import com.nic.transfer.domain.models.user.User;
import com.nic.transfer.domain.ports.out.AuthorizePort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class HttpAuthorizerAdapter implements AuthorizePort {

    private final RestClient restClient;

    public HttpAuthorizerAdapter(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://util.devi.tools/api/v2")
                .defaultHeader("User-Agent", "Mozilla/5.0")
                .build();
    }

    @Override
    public boolean isAuthorized(User payer, BigDecimal amount) {
        try {
            var response = restClient.get()
                    .uri("/authorize")
                    .retrieve()
                    .body(Map.class);

            if (response != null && "success".equals(response.get("status"))) {
                Map<String, Object> data = (Map<String, Object>) response.get("data");
                return Boolean.TRUE.equals(data.get("authorization"));
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
