package com.szczynk.simsppob.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopupRequest {

    @Schema(example = "100000", format = "int32")
    @PositiveOrZero(message = "Top up tidak boleh lebih kecil dari 0")
    @JsonProperty("top_up_amount")
    private int topUpAmount;

    @Schema(example = "Nabung")
    private String description;
}
