package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        try  {
            Faculty faculty = facultyService.readFaculty(id);
            return ResponseEntity.ok(faculty);
        } catch (NoFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Faculty>> getFacultyByColor(@PathVariable("color") String color) {
        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
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
        return ResponseEntity.ok().build();
    }
}
