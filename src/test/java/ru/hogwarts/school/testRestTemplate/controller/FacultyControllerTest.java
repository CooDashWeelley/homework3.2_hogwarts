package ru.hogwarts.school.testRestTemplate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyDTO;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyController facultyController;

    @Test
    public void testAddFaculty() {
        Long id = 100L;
        String name = "test faculty";
        String color = "test color";
        FacultyDTO faculty = new FacultyDTO(id, name, color);
        ResponseEntity<FacultyDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/hogwarts/faculty",
                faculty,
                FacultyDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetFacultyById() {
        Long id = 10L;

        ResponseEntity<FacultyDTO> response = restTemplate.getForEntity(
                "/hogwarts/faculty/{id}",
                FacultyDTO.class,
                id
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    public void testGetFaculty() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/hogwarts/faculty",
                List.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetStudentByColor() {
        String color = "test color";

        ResponseEntity<List> response = restTemplate.getForEntity(
                "/hogwarts/faculty/studentsByColor/{color}",
                List.class,
                color
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
//        List<StudentDTO> students = response.getBody();
//        assertThat(students.stream()
//                .filter(e->e.getFacultyColor().equals(color))
//                .toList()).isEqualTo(true);
    }

    @Test
    public void testPutFaculty() {
        Long id = 10L;
        String name = "new test faculty";
        String color = "new test color";
        FacultyDTO newFaculty = new FacultyDTO(id, name, color);

        restTemplate.put(
                "/hogwarts/faculty",
                newFaculty,
                FacultyDTO.class
        );
        ResponseEntity<FacultyDTO> response = restTemplate.getForEntity(
                "/hogwarts/faculty/{id}",
                FacultyDTO.class,
                id
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(newFaculty);
    }

    @Test
    public void testDeleteFaculty() {
        Long id = 202L;
        restTemplate.delete("/hogwarts/faculty/{id}", id);
        FacultyDTO response = restTemplate.getForObject(
                "/hogwarts/faculty/{id}",
                FacultyDTO.class,
                id
        );
        assertThat(response).isNull();
    }
}
