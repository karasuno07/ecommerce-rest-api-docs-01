package vn.alpaca.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import vn.alpaca.ecommerce.entity.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTO {

    private String username;

    private String firstName;

    private String lastName;

    private boolean gender;

    private String email;

    private String phoneNumber;

    private String image;

    private Set<Role> roles;

    public Set<String> getRoles() {
        roles.forEach(System.out::println);
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
