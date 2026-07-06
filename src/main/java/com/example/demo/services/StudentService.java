package com.example.demo.services;

import com.example.demo.dto.CreateStudentReqDTO;
import com.example.demo.dto.CreateStudentRespDTO;
import com.example.demo.dto.UpdateStudentReqDTO;
import com.example.demo.dto.UpdateStudentRespDTO;
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



    public CreateStudentRespDTO createStudent(CreateStudentReqDTO studentreqDto){


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

    public List<CreateStudentRespDTO> getallStudents(){

        List<Student> studentList2 = studentrepository.findByDeletedIsFalse();

        return studentList2.stream()
                .map(this::mapToDto)
                .toList();
    }


    public Student updatestudent(Long id, UpdateStudentReqDTO studentReq){
        Optional<Student> existingstudent = studentrepository.findById(id);

        if(existingstudent.isEmpty()) return null;

        Student studentTosave = existingstudent.get();
        studentTosave.setName(studentReq.getName());
        studentTosave.setAge(studentReq.getAge());
        studentTosave.setDepartment(studentReq.getDepartment());
        studentTosave.setRoll_no(studentReq.getRoll_no());


        Student Finalstudent = studentrepository.save(studentTosave);

        return Finalstudent;

    }

    private Student mapToEntity(CreateStudentReqDTO studentrequestdto){
        Student student = new Student();
        student.setName(studentrequestdto.getName());
        student.setAge(studentrequestdto.getAge());
        student.setDepartment(studentrequestdto.getDepartment());
        student.setRoll_no(studentrequestdto.getRoll_no());
        student.setEmail(studentrequestdto.getEmail());

        return student;
    }
    private CreateStudentRespDTO mapToDto(Student student){
        CreateStudentRespDTO studentRespDTO = new CreateStudentRespDTO();
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
    private UpdateStudentRespDTO mapToUpdateDto(Student student){
        UpdateStudentRespDTO updateStudentRespDTO = new UpdateStudentRespDTO();
        updateStudentRespDTO.setName(student.getName());
        updateStudentRespDTO.setAge(student.getAge());
        updateStudentRespDTO.setDepartment(student.getDepartment());
        updateStudentRespDTO.setRoll_no(student.getRoll_no());
        updateStudentRespDTO.setMessage("Student Record Updated Successfully");
        updateStudentRespDTO.setUpdatedAt(student.getCreatedAt());

        return updateStudentRespDTO;


    }
}
