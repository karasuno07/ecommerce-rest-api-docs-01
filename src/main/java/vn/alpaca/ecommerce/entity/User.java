package vn.alpaca.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Boolean gender;

    private String email;

    private String phoneNumber;

    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Users_Roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY)
    private Cart cart;

    public void addRole(Role role) {
        if (roles == null) roles = new LinkedHashSet<>();
        roles.add(role);
    }

    public boolean isCustomer() {
        return roles.stream().anyMatch(role -> role.getName().equals("ROLE_CUSTOMER"));
    }

    public boolean isManager() {
        return roles.stream().anyMatch(role -> role.getName().equals("ROLE_MANAGER"));
    }

    public boolean isAdmin() {
        return roles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

    @Override
    public String toString() {
        return String.format("\nId: %d | Username: %s | Password: %s | First Name: %s | Last Name: %s | gender: %d | email: %s | phoneNumber: %s | image: %s"
                , id, username, password, firstName, lastName, gender, email, phoneNumber, image);
    }
}
