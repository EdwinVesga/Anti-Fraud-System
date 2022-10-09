package antifraud.controller;

import antifraud.dto.*;
import antifraud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDetailResponseDTO> addUser(@Valid @RequestBody UserDetailRequestDTO user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDetailResponseDTO>> getRegisteredUsers() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<DeleteUserResponseDTO> deleteUser(@Valid @NotBlank @PathVariable String username) {
        return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.OK);
    }

    @PutMapping("/role")
    public ResponseEntity<UserDetailResponseDTO> updateUserRole(@Valid @RequestBody UpdateRoleRequestDTO updateRoleRequestDTO) {
        return new ResponseEntity<>(userService.updateUserRol(updateRoleRequestDTO), HttpStatus.OK);
    }

    @PutMapping("/access")
    public ResponseEntity<UpdateAccessResponseDTO> updateUserAcountAccess(@Valid @RequestBody UpdateAccessRequestDTO updateAccessRequestDTO) {
        return new ResponseEntity<>(userService.updateUserAccountAccess(updateAccessRequestDTO), HttpStatus.OK);
    }
}
