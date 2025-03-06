package com.example.school.result;

import java.util.Date;

import com.example.school.classes.Classes;
import com.example.school.subjects.Subjects;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(
    name = "_result"
)
public class Result {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Subjects subjects;

    @OneToOne
    private Classes classes;

    @OneToOne
    private Teacher teacher;

    private Date date;

    private String type;

    private int score;
}
