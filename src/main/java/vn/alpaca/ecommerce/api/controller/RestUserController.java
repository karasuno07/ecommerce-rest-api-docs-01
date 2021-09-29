package vn.alpaca.ecommerce.api.controller;

import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.ecommerce.dto.UserDTO;
import vn.alpaca.ecommerce.entity.User;
import vn.alpaca.ecommerce.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class RestUserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public RestUserController(
            UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ApiOperation(
            value = "Find all/paginated users",
            notes = "Provide 'page' and 'size' as param " +
                    "to get paginated users"
    )
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> page,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> size
    ) {
        Integer pageNumber = page.orElse(null);
        Integer pageSize = size.orElse(5);

        List<User> users;

        if (pageNumber != null) {
            users  = userService.findPaginatedAccounts(
                    PageRequest.of(pageNumber, pageSize)
            ).getContent();
        } else {
            users = userService.findAllAccounts();
        }

        List<UserDTO> transferUsers = users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(transferUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "Find an user by id")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") int id) {
        User rawUser = userService.findById(id);

        UserDTO transferUser = convertToUserDTO(rawUser);

        return new ResponseEntity<>(transferUser, HttpStatus.OK);
    }

    @GetMapping("/search/by-username/{username}")
    @ApiOperation(value = "Find an user by username")
    public ResponseEntity<UserDTO> findUserByUsername(
            @PathVariable("username") String username
    ) {
        User rawUser = userService.findByUsername(username);

        UserDTO transferUser = convertToUserDTO(rawUser);

        return new ResponseEntity<>(transferUser, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a new user from scratch")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody User user
    ) {
        User updatedUser = userService.saveAccount(user);

        UserDTO transferUser = convertToUserDTO(updatedUser);

        return new ResponseEntity<>(transferUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @ApiOperation(value = "Update an existing user")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("userId") int userId,
            @RequestBody User user
    ) {
        boolean existing =
                userService.findById(userId) != null &&
                        userId == user.getId();

        if (!existing) {
            throw new RuntimeException("Conflict data occurs");
        }

        User updatedUser = userService.saveAccount(user);

        UserDTO transferUser = convertToUserDTO(updatedUser);

        return new ResponseEntity<>(transferUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @ApiOperation(value = "Delete an existing user by id")
    public ResponseEntity<Boolean> deleteUser(
            @PathVariable("userId") int userId) {
        userService.deleteAccountById(userId);

        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
