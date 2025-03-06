package com.example.school.student;

import com.example.school.address.Address;
import com.example.school.classes.Classes;

import jakarta.persistence.Column;
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
    name = "_student"
)
public class Student {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
        name = "student_id",
        nullable = false
    )
    private String student_id;

    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    @Column(
        name = "email",
        nullable = false
    )
    private String email;

    @Column(
        name = "photo",
        nullable = false
    )
    private String photo;

    @Column(
        name = "phone",
        nullable = false
    )
    private String phone;

    @Column(
        name = "grade"
    )
    private int grade;

    @OneToOne
    private Classes classes;

    @OneToOne
    private Address address;
}
