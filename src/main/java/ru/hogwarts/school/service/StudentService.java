package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.MapperModel;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.AgeLessOneException;
import ru.hogwarts.school.exception.IncorrectAgeException;
import ru.hogwarts.school.exception.NoFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final MapperModel mapper;

    public StudentService(StudentRepository studentRepository, MapperModel mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }
    //crud: create, read,  update, delete

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public StudentDTO readStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new NoFoundException("student not found");
        }
        return MapperModel.toStudentDTO(student.get());
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getAllStudent() {
        return studentRepository.findAll().stream()
                .map(MapperModel::toStudentDTO)
                .toList();
    }

    public List<Student> getStudentByAge(int age) {
        if (age < 1) {
            throw new AgeLessOneException("age less 1");
        }
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        if (min > max || min < 1) {
            throw new IncorrectAgeException("incorrect parameters");
        }
        return studentRepository.findByAgeBetween(min, max);
    }

    public String getFaculty(Long id) {
       return readStudent(id).getFaculty().toString();

    }

}
