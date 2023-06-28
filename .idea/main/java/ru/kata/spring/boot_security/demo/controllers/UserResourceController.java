package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.UserDto;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class UserResourceController {

    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserResourceController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public UserDto findByUsername(@PathVariable String username) {
        return new UserDto(userService.findByUsername(username));
    }

    @PostMapping("/user")
    public void create(@RequestBody UserDto user) {
        userService.saveUser(user);
    }


    @GetMapping("/userForDelete/{username}")
    public UserDto findByUsername1(@PathVariable String username) {
        return new UserDto(userService.findByUsername(username));
    }
    @PostMapping("/userForDelete")
    public void delete(@RequestBody UserDto user) {
        userService.deleteUserByUsername(user.getUsername());
    }
}
