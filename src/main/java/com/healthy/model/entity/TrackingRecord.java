package com.healthy.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tracking_records")
public class TrackingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "value", nullable = false)
    private Float value;

    @Column(name = "note")
    private String note;
}
