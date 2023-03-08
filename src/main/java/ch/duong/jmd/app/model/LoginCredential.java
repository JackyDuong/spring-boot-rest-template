package ch.duong.jmd.#APP_ABBREVIATION.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredential {
    private String username;
    private String password;
}
