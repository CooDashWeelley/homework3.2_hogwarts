package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.IncorrectIdException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/hogwarts/student")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //crud: create, read,  update, delete

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.ok(newStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        try {
            Student student = studentService.readStudent(id);
            return ResponseEntity.ok(student);
        } catch (IncorrectIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/age/{age}")
//    public ResponseEntity<List<Student>> getStudentByAge(@RequestParam("age") int age) {
//        return ResponseEntity.ok(studentService.getStudentByAge(age));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Student>> getAllStudent() {
//        return ResponseEntity.ok(studentService.getAllStudent());
//    }

    @PutMapping()
    public ResponseEntity<Student> putStudent(@RequestBody Student student) {
        try {
            Student student1 = studentService.updateStudent(student);
            return ResponseEntity.ok(student1);
        } catch (IncorrectIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
