package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.IncorrectIdException;
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
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        try {
            Faculty faculty = facultyService.readFaculty(id);
            return ResponseEntity.ok(faculty);
        } catch (IncorrectIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getFacultyByColor(@RequestParam("color") String color) {
        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @PutMapping()
    public ResponseEntity<Faculty> putFaculty(@RequestBody Faculty faculty) {
        try {
            Faculty faculty1 = facultyService.updateFaculty(faculty);
            return ResponseEntity.ok(faculty1);
        } catch (IncorrectIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facultyService.deleteFaculty(id));
        } catch (IncorrectIdException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
