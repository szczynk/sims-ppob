package com.szczynk.simsppob.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "Email tidak sesuai format")
    @NotBlank(message = "Email harus diisi")
    private String email;

    @NotBlank(message = "Password harus diisi")
    @Size(min = 8, message = "Password minimal {min} karakter")
    private String password;
}
