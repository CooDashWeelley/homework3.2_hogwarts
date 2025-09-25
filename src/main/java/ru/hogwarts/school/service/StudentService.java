package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NoFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

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
        if(studentRepository.findById(id).isEmpty()) {
            throw new NoFoundException("student not found");
        }
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentByAge(int age) {
        return studentRepository.findByAge(age);
    }

}
