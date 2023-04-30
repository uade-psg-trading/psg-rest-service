package integracionapp.psgtrading.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.dto.UserDTO;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import integracionapp.psgtrading.service.JwtService;
import integracionapp.psgtrading.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping
    public GenericDTO<Page<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserDTO> userDto = userService.findAll(pageable)
                .map(this::getUserDTO);
        return GenericDTO.success(userDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public GenericDTO<UserDTO> createUser(@RequestBody @Valid UserDTO user) {
        User u = userService.saveUser(user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getDni(), user.getLocation(),
                user.getPassword(), user.getTenantId());
        UserDTO userDto = getUserDTO(u);
        return GenericDTO.success(userDto);
    }

    @GetMapping("/me")
    public GenericDTO<UserDTO> getUserById(@RequestHeader("Authorization") String jwt) {
        JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);
        User user;
        try {
            user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
        return GenericDTO.success(getUserDTO(user));
    }

    @PutMapping("")
    public GenericDTO<UserDTO> updateUser(@RequestBody UserDTO user, @RequestHeader("Authorization") String jwt) {
        JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);
        User existingUser;
        try {
            existingUser = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
        if (!existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Emails do not match.");
        }
        User u = userService.updateUser(existingUser, user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getDni());
        return GenericDTO.success(getUserDTO(u));
    }

    private UserDTO getUserDTO(User u) {
        return objectMapper.convertValue(u, UserDTO.class);
    }

}
