package com.example.school.parent;

import com.example.school.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IParentRepositry extends JpaRepository<Parent,Long>{

}
