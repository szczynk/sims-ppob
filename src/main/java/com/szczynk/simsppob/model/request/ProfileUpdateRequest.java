package com.szczynk.simsppob.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {
    @Schema(example = "Great user")
    @NotBlank(message = "First name harus diisi")
    @JsonProperty("first_name")
    private String firstName;

    @Schema(example = "Nutech Integrasi")
    @NotBlank(message = "Last name harus diisi")
    @JsonProperty("last_name")
    private String lastName;
}
