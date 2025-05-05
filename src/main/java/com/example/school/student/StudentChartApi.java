package com.example.school.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentChartApi {
    int girls_count;
    int boys_count;
    int total_count;
}
