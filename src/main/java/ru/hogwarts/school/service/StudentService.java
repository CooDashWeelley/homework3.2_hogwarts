package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.MapperModel;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.AgeLessOneException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.IncorrectAgeException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private  StudentRepository studentRepository;
    private  MapperModel mapper;

//    public StudentService(){
//
//    }

    public StudentService(StudentRepository studentRepository, MapperModel mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        studentRepository.save(MapperModel.toNewStudent(studentDTO));
        return studentDTO;
    }

    public StudentDTO readStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("student not found");
        }
        return MapperModel.toStudentDTO(student.get());
    }

    public StudentDTO updateStudent(StudentDTO studentDTO) {
        studentRepository.save(MapperModel.toStudent(studentDTO));
        return studentDTO;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getAllStudent() {
        return studentRepository.findAll().stream()
                .map(MapperModel::toStudentDTO)
                .toList();
    }

    public List<StudentDTO> getStudentByAge(int age) {
        if (age < 1) {
            throw new AgeLessOneException("age less 1");
        }
        return MapperModel.toStudentDTOList(studentRepository.findByAge(age));
    }

    public List<StudentDTO> findByAgeBetween(int min, int max) {
        if (min > max || min < 1) {
            throw new IncorrectAgeException("incorrect parameters");
        }
        return MapperModel.toStudentDTOList(studentRepository.findByAgeBetween(min, max));
    }

    public FacultyDTO getFacultyByStudentId(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("student not found");
        }
        Faculty faculty = student.get().getFaculty();
        if (faculty == null) {
            throw new FacultyNotFoundException("faculty not found");
        }
        return MapperModel.toFacultyDTO(faculty);
    }
}
