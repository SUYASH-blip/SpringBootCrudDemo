package com.example.demo.Controller;

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

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        System.out.println("Inside Student Controller");
        System.out.println(student.getName());

        Student createdStudent = studentService.createStudent(student);
        System.out.println("Exiting Student Controller");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }


    @GetMapping("/get")
    public ResponseEntity<Student> getStudent(@RequestParam Long id) {
        Student studentResp = studentService.getStudent(id);

        if(studentResp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Student>> getallStudents(){
        List<Student> studentList = studentService.getallStudents();

        if(studentList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(studentList);

    }

    @PostMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestParam Long id,@RequestBody Student studentreq){
        Student updatedStudent = studentService.updatestudent(id,studentreq);

        if(updatedStudent == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    // read student

    // update student

    // delete student


}
