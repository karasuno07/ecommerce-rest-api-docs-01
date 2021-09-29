package vn.alpaca.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.alpaca.ecommerce.entity.User;
import vn.alpaca.ecommerce.entity.Role;

import java.util.List;

public interface UserService extends UserDetailsService {

    /* Account Service start */

    User findById(int id);

    User findByUsername(String username);

    List<User> findAllAccounts();

    Page<User> findPaginatedAccounts(Pageable pageable);

    User saveAccount(User user);

    void deleteAccount(User user);

    void deleteAccountById(int id);

    /* Account Service end */


    /* Role Service start */

    Role findByRole(String roleName);

    List<Role> findAllRoles();

    /* Role Service end */
}
