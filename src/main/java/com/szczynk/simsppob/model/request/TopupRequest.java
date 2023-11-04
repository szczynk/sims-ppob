package com.szczynk.simsppob.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopupRequest {
    @PositiveOrZero(message = "Top up tidak boleh lebih kecil dari 0")
    @JsonProperty("top_up_amount")
    private int topUpAmount;

    private String description;
}
