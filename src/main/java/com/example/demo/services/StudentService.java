package com.example.demo.services;

import com.example.demo.entity.Student;
import com.example.demo.repository.Studentrepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private Studentrepository studentrepository;

    public StudentService(Studentrepository studentrepository){
        this.studentrepository = studentrepository;
    }

    public Student createStudent(Student studentreq){

        studentreq.setDeleted(false);

        Student studentresp = studentrepository.save(studentreq);

        return studentresp;

    }

    public Student getStudent(Long id) {
        Optional<Student> studentResp = studentrepository.findByIdAndDeletedIsFalse(id);

        return studentResp.orElse(null);

    }

    public List<Student> getallStudents(){

        List<Student> studentList2 = studentrepository.findByDeletedIsFalse();

        if(studentList2.isEmpty()) return null;
        return studentList2;

    }

    public Student updatestudent(Long id,Student studentReq){
        Optional<Student> existingstudent = studentrepository.findById(id);

        if(!existingstudent.isPresent()) return null;

        Student studentTosave = existingstudent.get();
        studentTosave.setName(studentReq.getName());
        studentTosave.setAge(studentReq.getAge());
        studentTosave.setDepartment(studentReq.getDepartment());
        studentTosave.setRoll_no(studentReq.getRoll_no());
        studentTosave.setEmail(studentReq.getEmail());

        Student Finalstudent = studentrepository.save(studentTosave);

        return Finalstudent;

    }
}
