package antifraud.controller;

import antifraud.dto.DeleteUserResponseDTO;
import antifraud.dto.UserDetailRequestDTO;
import antifraud.dto.UserDetailResponseDTO;
import antifraud.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth/user")
    public ResponseEntity<UserDetailResponseDTO> addUser(@Valid @RequestBody UserDetailRequestDTO user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/api/auth/list")
    public ResponseEntity<List<UserDetailResponseDTO>> getRegisteredUsers() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @DeleteMapping("/api/auth/user/{username}")
    public ResponseEntity<DeleteUserResponseDTO> deleteUser(@Valid @NotBlank @PathVariable String username) {
        return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.OK);
    }

}
