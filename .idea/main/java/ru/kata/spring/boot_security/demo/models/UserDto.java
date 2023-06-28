package ru.kata.spring.boot_security.demo.models;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserDto {

    private String username;

    private String password;

    private String email;

    private String roles;

    public UserDto() {
    }

    public UserDto(String username, String password, String email, String roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public UserDto(User userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.email = userEntity.getEmail();
        String role = null;
        Set<Role> roleSet = userEntity.getRoles();
        Iterator iterator = roleSet.iterator();
        while(iterator.hasNext()){

            String roleFromSet = String.valueOf(iterator.next());
            String roleBuffer = role;
            if(role == null) {
                role = roleFromSet;
            }else{
                role = roleBuffer + ", " + roleFromSet;
            }
        }
        this.roles = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

