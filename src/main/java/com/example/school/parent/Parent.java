package com.example.school.parent;

import java.util.List;

import com.example.school.address.Address;
import com.example.school.student.Student;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    name = "_parent"
)
public class Parent {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

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
        name = "phone",
        nullable = false
    )
    private String phone;

    @OneToOne
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable
    private List<Student> student;


}
