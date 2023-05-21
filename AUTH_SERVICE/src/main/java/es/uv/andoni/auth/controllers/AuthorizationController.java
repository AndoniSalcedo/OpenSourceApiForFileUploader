package es.uv.andoni.auth.controllers;

import es.uv.andoni.auth.services.JwtService;
import es.uv.andoni.auth.services.UserService;
import es.uv.andoni.shared.domain.AuthenticationRequest;
import es.uv.andoni.shared.domain.TokenDTO;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

  /* @Autowired
  private PasswordEncoder pe; */

  @Autowired
  private UserService us;

  @Autowired
  private JwtService tp;

  @GetMapping("authorize")
  public Mono<ResponseEntity<?>> validate(ServerWebExchange exchange) {
    String header = exchange
      .getRequest()
      .getHeaders()
      .getFirst(HttpHeaders.AUTHORIZATION);
    String token = tp.getTokenFromHeader(header);
    try {
      if (!tp.isTokenExpired(token)) {
        return Mono.just(
          ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(
              new TokenDTO(
                tp.getUsernameFromToken(token),
                tp
                  .getAuthoritiesFromToken(token)
                  .stream()
                  .map(GrantedAuthority::getAuthority)
                  .collect(Collectors.toList())
              )
            )
        );
      } else {
        return Mono.just(
          ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token")
        );
      }
    } catch (Exception e) {
      return Mono.just(
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token")
      );
    }
  }

  @PostMapping("authenticate")
  public Mono<ResponseEntity<?>> login(
    @RequestBody AuthenticationRequest auth
  ) {
    return us
      .findByEmailMono(auth.getEmail())
      .map(user -> {
        //TODO: hash pass in db
        /* if (pe.matches(auth.getPassword(), user.getPassword())) { */
        if (auth.getPassword().compareTo(user.getPassword()) == 0) {
          Map<String, String> tokens = new HashMap<>();
          String accessToken =
            this.tp.generateAccessToken(
                user.getId().toString(),
                Arrays.asList(user.getRole())
              );
          String refreshToken =
            this.tp.generateRefreshToken(
                user.getId().toString(),
                Arrays.asList(user.getRole())
              );
          tokens.put("access_token", accessToken);
          tokens.put("refresh_token", refreshToken);
          HttpHeaders headers = new HttpHeaders();
          headers.add(
            HttpHeaders.CONTENT_TYPE,
            MediaType.APPLICATION_JSON_VALUE
          );
          headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
          return ResponseEntity.ok().headers(headers).body(tokens);
        } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
      })
      .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

  @PostMapping("refresh")
  public Mono<ResponseEntity<?>> refresh(ServerWebExchange exchange) {
    String header = exchange
      .getRequest()
      .getHeaders()
      .getFirst(HttpHeaders.AUTHORIZATION);
    try {
      String refreshToken = tp.getTokenFromHeader(header);
      if (!tp.isTokenExpired(tp.getTokenFromHeader(header))) {
        String accessToken =
          this.tp.generateAccessToken(
              tp.getUsernameFromToken(refreshToken),
              Arrays.asList(tp.getRolesFromToken(refreshToken))
            );
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        return Mono.just(ResponseEntity.ok().headers(headers).body(tokens));
      } else {
        return Mono.just(
          ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body("Invalid refresh token")
        );
      }
    } catch (Exception e) {
      return Mono.just(
        ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("Invalid refresh token")
      );
    }
  }
}
