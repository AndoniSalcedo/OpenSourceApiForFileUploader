package es.uv.andoni.data.services;

import es.uv.andoni.data.models.User;
import es.uv.andoni.data.repository.UserRepository;
import es.uv.andoni.shared.domain.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserDTO getUserWithRoleByEmail(String email) {
    Optional<User> result = userRepository.findUserByEmail(email);

    if (!result.isPresent()) {
      throw new EntityNotFoundException("User not found");
    }

    System.out.println(result.get().getDType());

    UserDTO userDto = new UserDTO();
    userDto.setId(result.get().getId());
    userDto.setPassword(result.get().getPassword());
    userDto.setRole(result.get().getDType());

    return userDto;
  }
}
