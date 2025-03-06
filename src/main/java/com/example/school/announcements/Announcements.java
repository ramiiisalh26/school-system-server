package com.example.school.announcements;

import java.util.Date;

import com.example.school.classes.Classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(
    name = "_announcements"
)
public class Announcements {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        name = "title"
    )
    private String title;

    @OneToOne
    @JoinColumn(name = "classes_id")
    private Classes classes;

    @Column(
        nullable = false,
        name = "date"
    )
    private Date date;
}
