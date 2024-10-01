package com.healthy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name ="FK_tracking_record_user"))
    private User user;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "value", nullable = false)
    private Float value;

    @Column(name = "note")
    private String note;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "goal_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_tracking_record_goal"))
    private Goal goal;
}
