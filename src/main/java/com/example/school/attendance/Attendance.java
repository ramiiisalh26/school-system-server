package com.example.school.attendance;

import com.example.school.courses.Courses;
import com.example.school.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "_attendance")
public class Attendance {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Courses courses;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private StatusOfAtte status;
}
