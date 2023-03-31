package integracionapp.psgtrading.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ NoSuchElementException.class })
    public ResponseEntity<Error> noSuchElementHandler(NoSuchElementException e, HttpServletRequest request) {
        return ResponseEntity.status(404).body(new Error("NOT_FOUND", e.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Error> noSuchElementHandler(IllegalArgumentException e, HttpServletRequest request) {
        return ResponseEntity.status(400).body(new Error("INVALID", "Invalid arguments: " + e.getMessage()));
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
}
