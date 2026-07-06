package com.example.demo.services;

import com.example.demo.dto.createStudentReqDTO;
import com.example.demo.dto.createStudentRespDTO;
import com.example.demo.entity.Student;
import com.example.demo.repository.Studentrepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private Studentrepository studentrepository;

    public StudentService(Studentrepository studentrepository){
        this.studentrepository = studentrepository;
    }



    public createStudentRespDTO createStudent(createStudentReqDTO studentreqDto){


        Student student = mapToEntity(studentreqDto);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        Student studentfinal = studentrepository.save(student);
        return mapToDto(studentfinal);


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

    private Student mapToEntity(createStudentReqDTO studentrequestdto){
        Student student = new Student();
        student.setName(studentrequestdto.getName());
        student.setAge(studentrequestdto.getAge());
        student.setDepartment(studentrequestdto.getDepartment());
        student.setRoll_no(studentrequestdto.getRoll_no());
        student.setEmail(studentrequestdto.getEmail());

        return student;
    }
    private createStudentRespDTO mapToDto(Student student){
        createStudentRespDTO studentRespDTO = new createStudentRespDTO();
        studentRespDTO.setName(student.getName());
        studentRespDTO.setAge(student.getAge());
        studentRespDTO.setDepartment(student.getDepartment());
        studentRespDTO.setRoll_no(student.getRoll_no());
        studentRespDTO.setEmail(student.getEmail());
        studentRespDTO.setCreatedAt(student.getCreatedAt());
        studentRespDTO.setUpdatedAt(student.getUpdatedAt());
        studentRespDTO.setMessage("Student record saved successfully");

        return studentRespDTO;

    }
}
