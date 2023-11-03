package com.szczynk.simsppob.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column("id")
    private Long id;

    @Column("invoice_number")
    private String invoiceNumber;

    @Column("transaction_type")
    private String transactionType;

    @Column("description")
    private String description;

    @Column("total_amount")
    private long totalAmount;

    @Column("created_on")
    private Date createdOn;

    @Column("user_id")
    private Long userId;

    @Column("service_code")
    private String serviceCode;
}
