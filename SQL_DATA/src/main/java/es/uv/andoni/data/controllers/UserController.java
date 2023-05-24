package es.uv.andoni.data.controllers;

import es.uv.andoni.data.services.UserService;
import es.uv.andoni.shared.domain.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @GetMapping("/{email}")
  public ResponseEntity<?> getByEmail(@PathVariable String email) {
    try {
      log.info("Getting user with id: " + email);
      UserDTO user = userService.getUserWithRoleByEmail(email);

      return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
