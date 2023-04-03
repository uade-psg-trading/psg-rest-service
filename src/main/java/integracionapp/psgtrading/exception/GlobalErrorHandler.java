package integracionapp.psgtrading.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Error> noSuchElementHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(". "));
        return ResponseEntity
                .status(400)
                .body(new Error(ErrorCode.INVALID_PARAMETER.name(), message));
    }

    @ExceptionHandler({ CustomRuntimeException.class })
    public ResponseEntity<Error> customRunTimeExceptionHandler(CustomRuntimeException e, HttpServletRequest request) {
        tryToLog(e, request);
        return ResponseEntity
                .status(e.getCode().getStatus())
                .body(new Error(e.getCode().name(), e.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Error> defaultHandler(Exception e, HttpServletRequest request) {
        log.error("Not handled error", e);
        return ResponseEntity.internalServerError().body(new Error("GENERAL", e.getMessage()));
    }

    @AllArgsConstructor
    @Getter
    public static class Error {
        private final String code;
        private final String description;
    }

    private void tryToLog(CustomRuntimeException e, HttpServletRequest request) {
        if (ErrorCode.GENERAL_ERROR.equals(e.getCode())) {
            //log.error("General error - Request {}", request.getHeader("x-request-id"), e);
            log.error("General error", e);
        }
    }
}
