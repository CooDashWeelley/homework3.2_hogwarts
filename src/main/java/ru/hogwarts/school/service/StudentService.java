package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.hogwarts.school.repository.specificRequest.AverageAge;
import ru.hogwarts.school.repository.specificRequest.NumberOfStudents;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;
    private MapperModel mapper;

    Object object = new Object();

//    public StudentService(){
//
//    }

    public StudentService(StudentRepository studentRepository, MapperModel mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        logger.info("Was invoked method createStudent");
        studentRepository.save(MapperModel.toNewStudent(studentDTO));
        return studentDTO;
    }

    public StudentDTO readStudent(Long id) {
        logger.info("Was invoke method readStudent");
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            logger.error("There is not student with id = {}", id);
            throw new StudentNotFoundException("student not found");
        }
        return MapperModel.toStudentDTO(student.get());
    }

    public StudentDTO updateStudent(StudentDTO studentDTO) {
        logger.info("Was invoke method updateStudent");
        studentRepository.save(MapperModel.toStudent(studentDTO));
        return studentDTO;
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoke method deleteStudent");
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getAllStudent() {
        logger.info("Was invoke method getAllStudent");
        return studentRepository.findAll().stream()
                .map(MapperModel::toStudentDTO)
                .toList();
    }

    public List<StudentDTO> getStudentByAge(int age) {
        logger.info("Was invoke method getStudentByAge");
        if (age < 1) {
            logger.error("Parameter age = {} less than 1", age);
            throw new AgeLessOneException("age less 1");
        }
        return MapperModel.toStudentDTOList(studentRepository.findByAge(age));
    }

    public List<StudentDTO> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method findByAgeBetween");
        if (min > max || min < 1) {
            logger.error("min > max or min < 1");
            throw new IncorrectAgeException("incorrect parameters");
        }
        return MapperModel.toStudentDTOList(studentRepository.findByAgeBetween(min, max));
    }

    public FacultyDTO getFacultyByStudentId(Long id) {
        logger.info("was invoked getFacultyByStudentId");
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            logger.error("return student is empty");
            throw new StudentNotFoundException("student not found");
        }
        Faculty faculty = student.get().getFaculty();
        if (faculty == null) {
            logger.error("return faculty is null");
            throw new FacultyNotFoundException("faculty not found");
        }
        return MapperModel.toFacultyDTO(faculty);
    }

    public List<NumberOfStudents> getNumberOfStudents() {
        logger.info("Was invoke method getNumberOfStudent");
        return studentRepository.getNumberOfStudents();
    }

    public List<AverageAge> getAverageAge() {
        logger.info("Was invoke method getAverageAge");
        return studentRepository.getAverageAge();
    }

    public List<StudentDTO> getLastFiveStudent() {
        logger.info("Was invoke method getLastFiveStudent");
        return MapperModel.toStudentDTOList(studentRepository.getLastFiveStudent());
    }

    public List<StudentDTO> getStudentStartWithLetter(String letter) {
        logger.info("was invoked method getStudentStartWithLetter");
        return studentRepository.findAll().stream()
                .parallel()
                .map(MapperModel::toStudentDTO)
                .filter(e -> e.getName().startsWith(letter))
                .sorted(Comparator.comparing(StudentDTO::getName))
                .peek(e -> e.setName(e.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    public Double getAverageAgeOfStudent() {
        logger.info("was invoked method getAverageAgeOfStudent");
        return studentRepository.findAll().stream()
                .parallel()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public void getParallel() {
        logger.info("was  invoked getParallel");
        List<StudentDTO> studentDTOs = studentRepository.findAll().stream()
                .map(MapperModel::toStudentDTO)
                .toList();

        studentDTOs.subList(0, 2).forEach(this::printName);

        new Thread(() -> {
            studentDTOs.subList(2, 4).forEach(this::printName);
        }).start();

        new Thread(() -> {
            studentDTOs.subList(4, 6).forEach(this::printName);
        }).start();

        logger.info("getParallel finished");
    }

    private void printName(StudentDTO student) {
        System.out.println(student.getName());
    }

    public void printStudentSync() {
        logger.info("was  invoked printStudentSync");
        List<StudentDTO> students = studentRepository.findAll().stream()
                .map(MapperModel::toStudentDTO)
                .toList();
        students.subList(0, 2).forEach(this::printNameSync);
        getStudentSync(students.subList(2, 4));
        getStudentSync(students.subList(4, 6));
        logger.info("printStudentSync finished");
    }

    private void getStudentSync(List<StudentDTO> students) {
        new Thread(() -> {
            students.forEach(this::printNameSync);
        }).start();
    }

    private synchronized void printNameSync(StudentDTO student) {
        System.out.println(student.getName());
    }
}
