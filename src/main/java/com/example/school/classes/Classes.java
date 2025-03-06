package com.example.school.classes;

import com.example.school.teacher.Teacher;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @ToString.Exclude
    private Teacher teacher;
}
