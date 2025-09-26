package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.AgeLessOneException;
import ru.hogwarts.school.exception.IncorrectAgeException;
import ru.hogwarts.school.exception.NoFoundException;
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
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        try {
            StudentDTO student = studentService.readStudent(id);
            return ResponseEntity.ok(student);
        } catch (NoFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudent(@RequestParam(required = false) Integer age,
                                                       @RequestParam(required = false) Integer min,
                                                       @RequestParam(required = false) Integer max) {
        try {
            if (age != null) {
                return ResponseEntity.ok(studentService.getStudentByAge(age));
            }
            if (min != null && max != null) {
                return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
            }
        } catch (AgeLessOneException | IncorrectAgeException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @PutMapping()
    public ResponseEntity<Student> putStudent(@RequestBody Student student) {
        Student student1 = studentService.updateStudent(student);
        if (student1 == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
