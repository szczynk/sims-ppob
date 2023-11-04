package com.szczynk.simsppob.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {
    @NotBlank(message = "First name harus diisi")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Last name harus diisi")
    @JsonProperty("last_name")
    private String lastName;
}
