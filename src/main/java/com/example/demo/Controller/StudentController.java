package com.example.demo.Controller;

import com.example.demo.dto.CreateStudentReqDTO;
import com.example.demo.dto.CreateStudentRespDTO;
import com.example.demo.dto.UpdateStudentReqDTO;
import com.example.demo.dto.UpdateStudentRespDTO;
import com.example.demo.entity.Student;
import com.example.demo.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<CreateStudentRespDTO> createStudent(@RequestBody CreateStudentReqDTO studentrequestdto) {

        CreateStudentRespDTO createdStudent = studentService.createStudent(studentrequestdto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student studentResp = studentService.getStudent(id);

        if(studentResp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<CreateStudentRespDTO>> getallStudents(){
        List<CreateStudentRespDTO> studentList = studentService.getallStudents();

        if(studentList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(studentList);

    }

    @PostMapping("/update")
    public ResponseEntity<UpdateStudentRespDTO> updateStudent(@RequestParam Long id, @RequestBody UpdateStudentReqDTO studentreq){
        UpdateStudentRespDTO updatedStudent = studentService.updatestudent(id,studentreq);

        if(updatedStudent == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id) {
        studentService.deleteStudentSoftly(id);

        return ResponseEntity.noContent().build();
    }

    // read student

    // update student

    // delete student


}
