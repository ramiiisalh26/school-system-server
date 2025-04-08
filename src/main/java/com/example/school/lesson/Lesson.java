package com.example.school.lesson;

import com.example.school.classes.Classes;
import com.example.school.courses.Courses;
import com.example.school.teacher.Teacher;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(
    name = "_lesson"
)
public class Lesson {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @OneToOne
    private Courses courses;

    @OneToOne
    private Classes classes;
    
    @OneToOne
    private Teacher teacher;

    private Date startDate;
}
