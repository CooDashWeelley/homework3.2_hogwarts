package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.IncorrectColorException;
import ru.hogwarts.school.exception.NoFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/hogwarts/faculty")
public class FacultyController {
    FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty newFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(newFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Long id) {
        try {
            FacultyDTO faculty = facultyService.readFaculty(id);
            return ResponseEntity.ok(faculty);
        } catch (NoFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/studentsByFacultyId/{id}")
    public ResponseEntity<List<StudentDTO>> getStudentsOfFacultyById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facultyService.getStudentsByFacultyId(id));
        } catch (NoFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FacultyDTO>> getFaculty(@RequestParam(required = false) String color,
                                                       @RequestParam(required = false) String name) {
        try {
            if (color != null) {
                return ResponseEntity.ok(facultyService.getFacultyByColor(color));
            }
//            if (name != null) {
//                return ResponseEntity.ok(facultyService.getFacultyByName(name));
//            }
        } catch (IncorrectColorException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("/studentsByColor/{color}")
    public ResponseEntity<List<StudentDTO>> getStudentByColor(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.getStudentsByFaculty(color));
    }

    @PutMapping()
    public ResponseEntity<Faculty> putFaculty(@RequestBody Faculty faculty) {
        Faculty faculty1 = facultyService.updateFaculty(faculty);
        if (faculty1 == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
