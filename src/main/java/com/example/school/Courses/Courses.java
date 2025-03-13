package com.example.school.Courses;

import java.util.List;

import com.example.school.result.Result;
import com.example.school.teacher.Teacher;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(
    name = "_courses"
)
public class Courses {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    @ManyToMany(
        mappedBy = "courses",
        fetch = FetchType.LAZY)
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Result> results;
}
