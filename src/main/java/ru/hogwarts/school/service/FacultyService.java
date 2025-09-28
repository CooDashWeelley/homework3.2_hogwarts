package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.MapperModel;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.IncorrectColorException;
import ru.hogwarts.school.exception.NoFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final MapperModel mapper;

    public FacultyService(FacultyRepository facultyRepository, MapperModel mapper) {
        this.facultyRepository = facultyRepository;
        this.mapper = mapper;
    }
    //crud: create, read,  update, delete

    public FacultyDTO createFaculty(FacultyDTO facultyDTO) {
         facultyRepository.save(MapperModel.toFaculty(facultyDTO));
        return  facultyDTO;
    }

    public FacultyDTO readFaculty(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new NoFoundException("faculty not found");
        }
        return MapperModel.toFacultyDTO(faculty.get());
    }

    public FacultyDTO updateFaculty(FacultyDTO facultyDTO) {
        facultyRepository.save(MapperModel.toFaculty(facultyDTO));
        return facultyDTO;
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<FacultyDTO> getFacultyByColor(String color) {
        if (color == null || color.isBlank()) {
            throw new IncorrectColorException("Incorrect color");
        }
        return facultyRepository.findByColorIgnoreCase(color).stream()
                .map(MapperModel::toFacultyDTO)
                .toList();
    }

    public List<FacultyDTO> getAllFaculty() {
        return facultyRepository.findAll().stream()
                .map(MapperModel::toFacultyDTO)
                .toList();
    }

    public List<StudentDTO> getStudentsByFaculty(String color) {
        return facultyRepository.findByColorIgnoreCase(color).stream()
                .map(Faculty::getStudentsByFaculty)
                .flatMap(Collection::stream)
                .map(MapperModel::toStudentDTO)
                .toList();
    }

    public List<StudentDTO> getStudentsByFacultyId(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new NoFoundException("faculty not found");
        }
        return faculty.get().getStudentsByFaculty().stream()
                .map(MapperModel::toStudentDTO)
                .toList();
    }
}
