package com.mycompany.userservice.controller;

import com.mycompany.userservice.model.User;
import com.mycompany.userservice.model.UserDto;
import com.mycompany.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {

        UserDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/user")
    public UserDto getUser() {
        User user = new User();
        user.setFullName("samadhan bhusnar");
        user.setEmail("sam@gmail.com");
        user.setPhone("9876543210");
        user.setRole("Customer");
        return userService.mapToUserDto(user);


    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    // get user by id
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userById = userService.getUserById(id);
        return ResponseEntity.ok(userById);

    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {

        // check user exists
        User oldUser = userService.mapToUser(userService.getUserById(id));
        User user = userService.updateUser(oldUser, userDto);
        UserDto returnValue = userService.mapToUserDto(user);
        return ResponseEntity.ok(returnValue);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        // check user exists
        User user = userService.mapToUser(userService.getUserById(id));
        userService.deleteUser(user);
        userService.mapToUserDto(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}


