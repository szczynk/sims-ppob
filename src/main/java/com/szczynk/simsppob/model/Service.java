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
@Table(name = "services")
public class Service {
    @Id
    @Column("service_code")
    private String serviceCode;

    @Column("service_name")
    private String serviceName;

    @Column("service_image")
    private String serviceImage;

    @Column("service_tariff")
    private Long serviceTariff;
}
