package com.szczynk.simsppob.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(example = "user@nutech-integrasi.com")
    @Email(message = "Email tidak sesuai format")
    @NotBlank(message = "Email harus diisi")
    private String email;

    @Schema(example = "abcdef1234")
    @NotBlank(message = "Password harus diisi")
    @Size(min = 8, message = "Password minimal {min} karakter")
    private String password;

    @Schema(example = "User")
    @NotBlank(message = "First name harus diisi")
    @JsonProperty("first_name")
    private String firstName;

    @Schema(example = "Nutech")
    @NotBlank(message = "Last name harus diisi")
    @JsonProperty("last_name")
    private String lastName;

}
