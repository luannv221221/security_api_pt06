package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100,unique = true,nullable = false)
    private String categoryName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean status;
}
