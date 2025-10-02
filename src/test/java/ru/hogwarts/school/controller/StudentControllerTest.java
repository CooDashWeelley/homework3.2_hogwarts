package ru.hogwarts.school.controller;

//import org.junit.jupiter.api.Assertions;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testAddStudent() {

        String name = "test";
        int age = 10;
        Student student = new Student(name, age);

        Assertions
                .assertThat(this.restTemplate.postForObject(
                        "http://localhost:" + port + "/hogwarts/student",
                        student,
                        Student.class))
                .isNotNull()
                .isEqualTo(student);
    }

    @Test
    public void testGetStudentById() {
        Long id = 202L;
        String name = "test";
        int age = 10;
        StudentDTO student = new StudentDTO(id, name, age, null, null, null);
        //в базе заранее есть студент с такими параметрами

        ResponseEntity<StudentDTO> response = restTemplate.getForEntity(
                "/hogwarts/student/{id}", StudentDTO.class, id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(student);
    }

    @Test
    public void testGetFacultyByStudentId() {
        Long facultyId = 10L;
        String facultyName = "testFaculty";
        String color = "testColor";
        FacultyDTO faculty = new FacultyDTO(facultyId, facultyName, color);
        Long studentId = 252L; //студенту с этим id заранее назначен тестовый факультет

        ResponseEntity<FacultyDTO> response = restTemplate.getForEntity(
                "/hogwarts/student/{id}/facultyByStudentId", FacultyDTO.class, studentId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(faculty);
    }

    @Test
    public void testGetStudent() {
        Assertions.assertThat(this.restTemplate.getForObject(
                        "http://localhost:" + port + "/hogwarts/student",
                        List.class))
                .isNotNull();
    }




}
