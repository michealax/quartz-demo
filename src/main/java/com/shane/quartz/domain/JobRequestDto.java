package com.shane.quartz.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDto {
    private String jobClassName;

    private String jobGroupName;

    private String cronExpression;
}
