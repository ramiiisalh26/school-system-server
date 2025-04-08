package com.example.school.parent;

import java.util.ArrayList;
import java.util.List;

import com.example.school.address.Address;
import com.example.school.student.Student;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(
    name = "_parent"
)
public class Parent {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Version
    private Integer version;

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
        name = "phone",
        nullable = false
    )
    private String phone;

    @OneToOne
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "student_parent",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @ToString.Exclude
    private List<Student> student = new ArrayList<>();

}
