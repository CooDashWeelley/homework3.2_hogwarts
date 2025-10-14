package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.AgeLessOneException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.IncorrectAgeException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.repository.specificRequest.AverageAge;
import ru.hogwarts.school.repository.specificRequest.NumberOfStudents;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/hogwarts/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.createStudent(studentDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        try {
            StudentDTO student = studentService.readStudent(id);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/facultyByStudentId")
    public ResponseEntity<FacultyDTO> getFacultyByStudentId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(studentService.getFacultyByStudentId(id));
        } catch (StudentNotFoundException | FacultyNotFoundException e) {
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

    @GetMapping("/numberOf")
    public ResponseEntity<List<NumberOfStudents>> getNumberOfStudent() {
        return ResponseEntity.ok(studentService.getNumberOfStudents());
    }

    @GetMapping("/avgAge")
    public ResponseEntity<List<AverageAge>> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/lastFive")
    public ResponseEntity<List<StudentDTO>> getLastFIveStudent() {
        return ResponseEntity.ok(studentService.getLastFiveStudent());
    }

    @GetMapping("/start{letter}")
    public ResponseEntity<List<StudentDTO>> getStudentStartWithLetter(@PathVariable String letter) {
        return ResponseEntity.ok(studentService.getStudentStartWithLetter(letter));
    }

    @GetMapping("/avgAge4_5")
    public ResponseEntity<Double> getAverageAgeOfStudent() {
        return ResponseEntity.ok(studentService.getAverageAgeOfStudent());
    }

    @PutMapping()
    public ResponseEntity<StudentDTO> putStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO student1 = studentService.updateStudent(studentDTO);
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
