package com.szczynk.simsppob.model.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    @JsonProperty("invoice_number")
    private String invoiceNumber;

    @JsonProperty("transaction_type")
    private String transactionType;

    private String description;

    @JsonProperty("total_amount")
    private long totalAmount;

    @JsonProperty("created_on")
    private Date createdOn;
}
