package com.mycompany.userservice.service;

import com.mycompany.userservice.exception.UserNotFoundException;
import com.mycompany.userservice.model.User;
import com.mycompany.userservice.model.UserDto;
import com.mycompany.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserDto userDto) {
        User user = mapToUser(userDto);
        return mapToUserDto(userRepository.save(user));
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
        return mapToUserDto(user);

    }

    public List<UserDto> getUsers() {
        List<User> userList = userRepository.findAll();
        if (!userList.isEmpty()) {
            return userList.stream().map(this::mapToUserDto).toList();
        }
        return Collections.emptyList();
    }

    public User updateUser(User oldUser, UserDto updatedUser) {
        oldUser.setFullName(updatedUser.getFullName());
        oldUser.setEmail(updatedUser.getEmail());
        oldUser.setPhone(updatedUser.getPhone());
        oldUser.setRole(updatedUser.getRole());
        oldUser.setPassword(updatedUser.getPassword());
        return userRepository.save(oldUser);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    // mapping from user to userdto
    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }

    // mapping from userdto to user

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        return user;
    }

}
