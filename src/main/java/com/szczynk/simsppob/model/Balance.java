package com.szczynk.simsppob.model;

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
@Table(name = "balances")
public class Balance {
    @Id
    @Column("id")
    private Long id;

    @Column("balance_amount")
    private Long balanceAmount;

    @Column("user_id")
    private Long userId;
}
