package com.example.school.assignment;

import java.util.Date;

import com.example.school.classes.Classes;
import com.example.school.courses.Courses;
import com.example.school.student.Student;
import com.example.school.teacher.Teacher;

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
    private Courses courses;
    
    @OneToOne
    private Classes classes;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Student student;

    private Date dueDate;
}
