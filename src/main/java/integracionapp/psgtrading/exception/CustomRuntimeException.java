package integracionapp.psgtrading.exception;

public class CustomRuntimeException extends RuntimeException{
    private final String code;
    private final String message;

    public CustomRuntimeException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
