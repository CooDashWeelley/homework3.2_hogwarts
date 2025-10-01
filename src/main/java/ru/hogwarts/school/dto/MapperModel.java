package ru.hogwarts.school.dto;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Component
public class MapperModel {
    public static StudentDTO toStudentDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getFaculty() != null ? student.getFaculty().getId() : null,
                student.getFaculty() != null ? student.getFaculty().getName() : null,
                student.getFaculty() != null ? student.getFaculty().getColor() : null
        );
    }

    public static Student toStudent(StudentDTO studentDTO) {
        return new Student(
                studentDTO.getName(),
                studentDTO.getAge()
        );
    }

    public static List<StudentDTO> toStudentDTOList(List<Student> students) {
        return students.stream()
                .map(MapperModel::toStudentDTO)
                .toList();
    }

    public static FacultyDTO toFacultyDTO(Faculty faculty) {
        return new FacultyDTO(
                faculty.getId(),
                faculty.getName(),
                faculty.getColor()
        );
    }

    public static Faculty toFaculty(FacultyDTO facultyDTO) {
        return new Faculty(
                facultyDTO.getName(),
                facultyDTO.getColor()
        );
    }
}
