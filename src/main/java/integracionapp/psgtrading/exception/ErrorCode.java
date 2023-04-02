package integracionapp.psgtrading.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_PARAMETER(400, "Invalid parameter"),
    INVALID_STATE(409, "Invalid state"),
    GENERAL_ERROR(500, "General error"),

    FORBIDDEN(403, "Forbidden");

    private final int status;
    private final String description;
}
