package integracionapp.psgtrading.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private final ErrorCode code;
    private final String message;

    public CustomRuntimeException(ErrorCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    public CustomRuntimeException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

}
