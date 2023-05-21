package es.uv.andoni.data.repository;

import es.uv.andoni.data.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT u FROM User u WHERE u.email = :email AND u.state = true")
  Optional<User> findUserByEmail(@Param("email") String email);
}
