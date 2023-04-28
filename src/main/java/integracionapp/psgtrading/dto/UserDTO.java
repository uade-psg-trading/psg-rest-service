package integracionapp.psgtrading.dto;

import integracionapp.psgtrading.model.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    @Schema(example = "John")
    private String firstName;

    @NotBlank
    @Schema(example = "Doe")
    private String lastName;

    @NotBlank
    @Schema(example = "user@mail.com")
    private String email;

    @NotBlank
    @Schema(example = "psg")
    private String tenantId;

    @NotBlank
    @Schema(example = "my-password")
    private String password;

    private Integer dni;

    private Location location;
}

