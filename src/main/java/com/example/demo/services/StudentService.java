package com.example.demo.services;

import com.example.demo.dto.CreateStudentReqDTO;
import com.example.demo.dto.CreateStudentRespDTO;
import com.example.demo.dto.UpdateStudentReqDTO;
import com.example.demo.dto.UpdateStudentRespDTO;
import com.example.demo.entity.Student;
import com.example.demo.exceptions.DuplicateResource;
import com.example.demo.exceptions.ResourceNotFoundException;
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
        if(emailExists(student)){
            throw new DuplicateResource("Email id already Exists!");
        }
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        Student studentfinal = studentrepository.save(student);
        return mapToDto(studentfinal);


    }

    public Student getStudent(Long id) {

        Student studentresp = studentrepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + "not Found"));

        return mapToDto(studentresp);


    }

    public List<CreateStudentRespDTO> getallStudents(){
        List<Student> studentList2 = studentrepository.findByDeletedIsFalse();

        return studentList2.stream()
                .map(this::mapToDto)
                .toList();
    }


    public UpdateStudentRespDTO updatestudent(Long id, UpdateStudentReqDTO studentReq){
        Student existingstudent = studentrepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + "not Found"));



        Student studentTosave = existingstudent;
        existingstudent.setName(studentReq.getName());
        existingstudent.setAge(studentReq.getAge());
        existingstudent.setDepartment(studentReq.getDepartment());
        existingstudent.setRoll_no(studentReq.getRoll_no());


        Student savedStudent = studentrepository.save(existingstudent);

        return mapToUpdateDto(savedStudent);

    }


    public Boolean deleteStudentSoftly(Long id) {
        Optional<Student> existingStudent =
                studentrepository.findByIdAndDeletedIsFalse(id);

        if (existingStudent.isEmpty()) return false;

        Student studentToSave = existingStudent.get();
        studentToSave.setDeleted(true);
        studentrepository.save(studentToSave);

        return true;
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

    public boolean emailExists(Student student){
        return studentrepository.existsByEmail(student.getEmail());
    }
}
