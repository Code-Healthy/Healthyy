package com.healthy.dto;

import com.healthy.model.enums.Frequency;
import lombok.Data;
@Data

public class HabitCreateUpdateDTO {
    private Integer id;
    private String name;
    private String description;
    private Frequency frequency;

    private Integer habitTypeId;
}