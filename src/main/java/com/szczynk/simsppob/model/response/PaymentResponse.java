package com.szczynk.simsppob.model.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    @JsonProperty("invoice_number")
    private String invoiceNumber;

    @JsonProperty("service_code")
    private String serviceCode;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("total_amount")
    private long totalAmount;

    @JsonProperty("created_on")
    private Date createdOn;
}
