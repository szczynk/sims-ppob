package com.szczynk.simsppob.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotBlank(message = "Service code harus diisi")
    @JsonProperty("service_code")
    private String serviceCode;
}
