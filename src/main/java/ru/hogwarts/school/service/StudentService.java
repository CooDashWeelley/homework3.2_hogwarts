package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.IncorrectIdException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private Long id = 0L;
    private Map<Long, Student> students = new HashMap<>();

    //crud: create, read,  update, delete

    public Student createStudent(Student student) {
        if (student == null) {
            throw new RuntimeException();
        }
        student.setId(id);
        students.put(id++, student);
        return student;
    }

    public Student readStudent(Long id) {
        if (id > students.size()) {
            throw new IncorrectIdException();
        }
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (id > students.size() || student == null) {
            throw new IncorrectIdException();
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(Long id) {
        if (id > students.size()) {
            throw new IncorrectIdException();
        }
        return students.remove(id);
    }

    public List<Student> getStudentByAge(int age) {
        if (age < 1) {
            throw new IncorrectIdException();
        }
        return students.values().stream()
                .filter(e -> e.getAge() == age)
                .toList();
    }

    public List<Student> getAllStudent() {
        return students.values().stream().toList();
    }
}
