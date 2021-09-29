package vn.alpaca.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.alpaca.ecommerce.dao.UserRepo;
import vn.alpaca.ecommerce.dao.RoleRepo;
import vn.alpaca.ecommerce.entity.User;
import vn.alpaca.ecommerce.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo,
                           RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user;

        try {
            user = findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(
                    "Did not find username: " + username
            );
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public User findById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Not found account with id " + id)
                );
    }

    @Override
    public User findByUsername(String username) {
        // this method is used to call ajax to validate username exists or not
        // it's valid to be null, so it's not necessary to throw an exception
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> findAllAccounts() {
        return userRepo.findAll();
    }

    @Override
    public Page<User> findPaginatedAccounts(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public User saveAccount(User user) {
        return userRepo.save(user);
    }

    @Override
    public void deleteAccount(User user) {
        userRepo.delete(user);
    }

    @Override
    public void deleteAccountById(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public Role findByRole(String roleName) {
        return roleRepo.findByName(roleName);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepo.findAll();
    }


}
