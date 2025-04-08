package com.example.school.classes;

import com.example.school.assignment.Assignment;
import com.example.school.result.Result;
import com.example.school.student.Student;
import com.example.school.teacher.Teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data 
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(
    name = "_classes"
)
public class Classes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    @Column(
        name = "capacity",
        nullable = false
    )
    private int capacity;

    @Column(
        name = "grade",
        nullable = false
    )
    private int grade;

    @Column(
        name = "superVisor",
        nullable = false
    )
    private String super_visor;

    @ManyToMany(mappedBy = "classes",fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<Teacher> teacher;

    @ManyToMany(mappedBy = "classes")
    private List<Student> student = new ArrayList<>();

    @OneToMany(mappedBy = "classes", fetch = FetchType.LAZY)
    private List<Result> results = new ArrayList<>();

    @OneToOne
    private Assignment assignment;
}
