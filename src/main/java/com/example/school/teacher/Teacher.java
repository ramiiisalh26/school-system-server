package com.example.school.teacher;

import java.util.List;

// import com.example.school.address.Address;
import com.example.school.address.Address;
import com.example.school.classes.Classes;
import com.example.school.subjects.Subjects;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(
    name = "_teacher"
)
public class Teacher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "teacher_id",
        nullable = false
    )
    private String teacher_id;

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
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subjects> subjects;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @ToString.Exclude
    private List<Classes> classes;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Address address;
}
