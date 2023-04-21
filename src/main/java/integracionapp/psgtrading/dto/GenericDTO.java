package integracionapp.psgtrading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@lombok.Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class GenericDTO<T> {
    private String message;
    private T data;
    private boolean success;


    public static <T> GenericDTO<T> success(T data) {
        return GenericDTO.<T>builder()
                .data(data)
                .success(true)
                .build();
    }

    public static <T> GenericDTO<T> error(String message) {
        return GenericDTO.<T>builder()
                .message(message)
                .success(false)
                .build();
    }
}
