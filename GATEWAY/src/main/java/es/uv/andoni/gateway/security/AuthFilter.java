package es.uv.andoni.gateway.security;

import com.google.common.net.HttpHeaders;
import es.uv.andoni.shared.domain.TokenDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter
  extends AbstractGatewayFilterFactory<AuthFilter.Config> {

  @Autowired
  private WebClient.Builder client;

  @Value("${url.auth.service}")
  private String auth_url;

  private static final String VALUE = "roles";

  public AuthFilter() {
    super(Config.class);
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return Collections.singletonList(VALUE);
  }

  public static class Config {

    private List<String> roles;

    public Config() {
      roles = new ArrayList<>();
    }

    public List<String> getRoles() {
      return roles;
    }

    public void setRoles(List<String> roles) {
      this.roles = roles;
    }
  }

  public static Boolean checkArray(List<String> roles, List<String> userRoles) {
    if (roles.isEmpty()) {
      return true;
    }

    for (String element : roles) {
      if (userRoles.contains(element)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Config newConfig() {
    return new Config();
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpResponse response = exchange.getResponse();

      if (
        !exchange
          .getRequest()
          .getHeaders()
          .containsKey(HttpHeaders.AUTHORIZATION)
      ) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
      }

      String authHeader = exchange
        .getRequest()
        .getHeaders()
        .get(HttpHeaders.AUTHORIZATION)
        .get(0);
      if (!authHeader.contains("Bearer")) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
      }

      try {
        return client
          .build()
          .get()
          .uri(auth_url + "/auth/authorize")
          .header(HttpHeaders.AUTHORIZATION, authHeader)
          .retrieve()
          .onStatus(
            httpStatus -> httpStatus.value() != HttpStatus.ACCEPTED.value(),
            error -> {
              return Mono.error(new Throwable("UNAUTHORIZED"));
            }
          )
          .toEntity(TokenDTO.class)
          .flatMap(entity -> {
            if (entity.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
              response.setStatusCode(HttpStatus.UNAUTHORIZED);
              return response.setComplete();
            }

            List<String> userRoles = entity.getBody().getRoles();
            config.getRoles().stream().forEach(r -> System.out.print(r));

            if (!checkArray(config.getRoles(), userRoles)) {
              response.setStatusCode(HttpStatus.UNAUTHORIZED);
              return response.setComplete();
            }
            exchange
              .getRequest()
              .mutate()
              .header("x-auth-user-id", entity.getBody().getId());
            return chain.filter(exchange);
          });
      } catch (Throwable e) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
      }
    };
  }
}
