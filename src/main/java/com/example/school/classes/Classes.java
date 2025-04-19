package com.example.school.classes;

import com.example.school.courses.Courses;
import com.example.school.result.Result;
import com.example.school.student.Student;
import com.example.school.teacher.Teacher;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
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

    @Column(
            name = "semester"
//            nullable = false
    )
    private String semester;

    @Embedded
    private Schedule schedule;

    private Class_Status status;

    @ManyToMany(mappedBy = "classes")
    @JsonManagedReference
    private List<Courses> courses = new ArrayList<>();

    @ManyToMany(mappedBy = "classes",fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    @JsonIgnore
    private List<Teacher> teacher = new ArrayList<>();

    @ManyToMany(mappedBy = "classes")
    @JsonManagedReference
    private List<Student> student = new ArrayList<>();

    @OneToMany(mappedBy = "classes", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Result> results = new ArrayList<>();

}
