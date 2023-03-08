package ch.duong.jmd.#APP_ABBREVIATION.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HttpError {
    private String code;
    private String message;
    private LocalDateTime time;

    private HttpError(#UPPER_CASE_APP_ABBREVIATIONError error){
        code = error.name();
        message = error.getMsg();
        time = LocalDateTime.now();
    }

    public static HttpError create(final #UPPER_CASE_APP_ABBREVIATIONError error){
        return new HttpError(error);
    }

    public static HttpError create(final #UPPER_CASE_APP_ABBREVIATIONError error, final String message){
        var httpError = new HttpError(error);
        httpError.setMessage(message);

        return httpError;
    }
}
