package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Studentrepository extends JpaRepository<Student,Long> {

Optional<Student> findByIdAndDeletedIsFalse(Long id);

List<Student> findByDeletedIsFalse();

}
