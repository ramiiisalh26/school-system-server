package com.example.school.assignment;

import java.util.Date;

import com.example.school.classes.Classes;
import com.example.school.courses.Courses;
import com.example.school.student.Student;
import com.example.school.teacher.Teacher;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(
    name = "_assignment"
)
public class Assignment {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Courses courses;
    
    @OneToOne
    @JoinColumn(
            name = "class_id"
    )
    private Classes classes;

    @ManyToOne
    @JsonBackReference
    private Teacher teacher;

    @ManyToOne
    @JsonBackReference
    private Student student;

    private Date dueDate;
}
