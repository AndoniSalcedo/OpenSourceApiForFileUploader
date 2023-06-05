package es.uv.andoni.auth.services;

import es.uv.andoni.shared.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

  @Autowired
  UserService us;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return us.findByEmailMono(username).map(this::toAuthUser);
  }

  private User toAuthUser(UserDTO u) {
    return new User(
      u.getId().toString(),
      u.getPassword(),
      AuthorityUtils.createAuthorityList(u.getRole())
    );
  }
}
