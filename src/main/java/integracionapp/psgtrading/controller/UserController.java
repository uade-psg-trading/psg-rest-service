package integracionapp.psgtrading.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.UserDTO;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

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
                user.getLastName(), user.getDni(), user.getLocation(), user.getPassword());
        UserDTO userDto = getUserDTO(u);
        return GenericDTO.success(userDto);
    }

    @GetMapping("/{id}")
    public GenericDTO<UserDTO> getUserById(@PathVariable long id){
        return GenericDTO.success(getUserDTO(userService.findById(id)));
    }

    @PutMapping("/{id}")
    public GenericDTO<UserDTO> updateUser(@RequestBody UserDTO user){
        User u = userService.updateUser(user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getDni());
        return GenericDTO.success(getUserDTO(u));
    }

    private UserDTO getUserDTO(User u) {
        return objectMapper.convertValue(u, UserDTO.class);
    }

}
