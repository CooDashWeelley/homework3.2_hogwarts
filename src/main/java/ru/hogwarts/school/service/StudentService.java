package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.IncorrectIdException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    //crud: create, read,  update, delete

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student readStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

//    public List<Student> getStudentByAge(int age) {
//        if (age < 1) {
//            throw new IncorrectIdException();
//        }
//        return students.values().stream()
//                .filter(e -> e.getAge() == age)
//                .toList();
//    }
//
//    public List<Student> getAllStudent() {
//        return students.values().stream().toList();
//    }
}
