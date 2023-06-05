package es.uv.andoni.auth.services;

import es.uv.andoni.shared.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  @Autowired
  WebClient.Builder client;

  @Value("${url.data.service}")
  private String data_api;

  public Mono<UserDTO> findByEmailMono(String email) {
    Mono<UserDTO> u = client
      .build()
      .get()
      .uri(data_api + "/users/" + email)
      .retrieve()
      .bodyToMono(UserDTO.class)
      .switchIfEmpty(
        Mono.defer(() ->
          Mono.error(new UsernameNotFoundException("User Not Found"))
        )
      );

    return u;
  }
}
