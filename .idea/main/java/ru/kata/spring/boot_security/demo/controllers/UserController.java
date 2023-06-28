package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.UserDto;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        User userEntity = userDetailsService.findByUsername(principal.getName());
        UserDto userDto = new UserDto(userEntity);
        model.addAttribute("userDto", userDto);
        return "user";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }


    @GetMapping("/admin/allUsers")
    public String showAllUsers(Model model) {
        List<User> userEntityList = userDetailsService.allUsers();

        List<UserDto> userDtoList = new ArrayList<>();

        Iterator iterator = userEntityList.iterator();
        while (iterator.hasNext()) {
            User userEntity = (User) iterator.next();
            userDtoList.add(new UserDto(userEntity));
        }

        model.addAttribute("allUsers", userDtoList);
        return "AllUsers1";
    }


    @GetMapping("/admin/allUsers/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "UserCreate1";
    }

    @PostMapping("/admin/create")
    public String createUser(UserDto user) {
        userDetailsService.saveUser(user);
        return "redirect:/admin/allUsers";
    }


    @GetMapping("/admin/update/{username}")
    public String updateUserForm(@PathVariable("username") String username, Model model) {
        User userEntity = userDetailsService.findByUsername(username);
        UserDto userDto = new UserDto(userEntity);
        model.addAttribute("userDto", userDto);
        return "userUpdate";
    }

    @PostMapping("/admin/update")
    public String updateUser(UserDto user) {
        userDetailsService.saveUser(user);
        return "redirect:/admin/allUsers";
    }


// метод, который работал без bootstrap
//    @GetMapping("/admin/delete/{username}")
//    public String deleteUser(@PathVariable("username") String username) {
//        userDetailsService.deleteUserByUsername(username);
//        return "redirect:/admin/allUsers";
//    }


}
