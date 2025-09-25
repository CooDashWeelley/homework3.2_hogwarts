package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.IncorrectIdException;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {
    private Long id = 0L;
    private Map<Long, Faculty> faculties = new HashMap<>();

    //crud: create, read,  update, delete

    public Faculty createFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new RuntimeException();
        }
        faculty.setId(id);
        faculties.put(id++, faculty);
        return faculty;
    }

    public Faculty readFaculty(Long id) {
        if (id > faculties.size()) {
            throw new IncorrectIdException();
        }
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (id > faculties.size() || faculty == null) {
            throw new IncorrectIdException();
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id) {
        if (id > faculties.size()) {
            throw new IncorrectIdException();
        }
        return faculties.remove(id);
    }

    public List<Faculty> getFacultyByColor(String color) {
        return faculties.values().stream()
                .filter(e -> e.getColor().equals(color))
                .toList();
    }

    public List<Faculty> getAllFaculty() {
        return faculties.values().stream().toList();
    }
}
