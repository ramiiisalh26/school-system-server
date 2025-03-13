package com.example.school.student;

import com.example.school.address.Address;
import com.example.school.classes.Classes;

import com.example.school.parent.Parent;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Version
    private Integer version;

    @Column(
        name = "student_id",
        nullable = false
    )
    private String student_id;

    @Column(
        name = "first_name",
        nullable = false
    )
    private String first_name;

    @Column(
            name = "middle_name",
            nullable = false
    )
    private String middle_name;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String last_name;

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

    @ManyToMany
    @JoinTable(
            name = "student_class",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    @ToString.Exclude
    private List<Classes> classes = new ArrayList<>();

    @OneToOne
    private Address address;

    @ManyToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Parent> parents = new ArrayList<>();
}
