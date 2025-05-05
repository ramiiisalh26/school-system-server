package com.example.school.courses;

import java.util.ArrayList;
import java.util.List;

import com.example.school.assignment.Assignment;
import com.example.school.attendance.Attendance;
import com.example.school.classes.Classes;
import com.example.school.result.Result;
import com.example.school.student.Student;
import com.example.school.teacher.Teacher;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

//@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(
    name = "_courses"
)
@Setter
@Getter
@ToString
public class Courses {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    @Column(name = "course_code")
    private String course_code;

    private String department;

    @ManyToMany(
        mappedBy = "courses",
        fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Result> results;

    @OneToMany(mappedBy = "courses",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Assignment> assignments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "classes_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    @JsonBackReference
    private List<Classes> classes = new ArrayList<>();

    @OneToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Attendance> attendances = new ArrayList<>();
}
