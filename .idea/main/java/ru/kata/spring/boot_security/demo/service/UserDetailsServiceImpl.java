package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.UserDto;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        return user;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void saveUser(UserDto savedUser) {
        Set<Role> roles = getRoles(savedUser.getRoles());
        User user = userRepository.findByUsername(savedUser.getUsername());
        if (user != null) {
            user.setPassword(savedUser.getPassword());
            user.setEmail(savedUser.getEmail());
            user.setRoles(roles);
        } else {
            user = new User();
            user.setUsername(savedUser.getUsername());
            user.setPassword(savedUser.getPassword());
            user.setEmail(savedUser.getEmail());
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Set<Role> getRoles(String roleNamesToSearch) {
            String[] roleNamesArray = roleNamesToSearch.split("\\,");
            Set<Role> roleSet = new HashSet<>();
            for (String roleName : roleNamesArray) {
                Role role = roleRepository.findByRoleName(roleName.trim());
                roleSet.add(role);
                }
            return roleSet;
    }


    public void deleteUserByUsername(String username) {
        User deletedUser = userRepository.findByUsername(username);
        userRepository.delete(deletedUser);
    }

}
