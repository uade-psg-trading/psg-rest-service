package integracionapp.psgtrading.exception;

public class ClientException extends CustomRuntimeException{
    public ClientException(String code, String message) {
        super(code, message);
    }
}
