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
@Table(name = "banners")
public class Banner {
    @Id
    @Column("id")
    private Long id;

    @Column("banner_name")
    private String bannerName;

    @Column("banner_image")
    private String bannerImage;

    @Column("description")
    private String description;
}
