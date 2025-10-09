package ru.hogwarts.school.WebMvcTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void testAddStudent() throws Exception {
        Long id = 1L;
        String name = "test";
        int age = 111;
        StudentDTO studentDTO = new StudentDTO(id, name, age, null, null, null);

        JSONObject studentJSON = new JSONObject();
        studentJSON.put("id", id);
        studentJSON.put("name", name);
        studentJSON.put("age", age);

        Mockito.when(studentService.createStudent(any(StudentDTO.class))).thenReturn(studentDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/hogwarts/student")
                        .content(studentJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Long id = 1L;
        String name = "test";
        int age = 111;
        StudentDTO studentDTO = new StudentDTO(id, name, age, null, null, null);

        JSONObject studentJSON = new JSONObject();
        studentJSON.put("id", id);
        studentJSON.put("name", name);
        studentJSON.put("age", age);

        Mockito.when(studentService.readStudent(any(Long.class))).thenReturn(studentDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hogwarts/student/{id}", id)
                        .content(studentJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testGetFacultyByStudentId() throws Exception {
        Long facultyId = 10L;
        String facultyName = "test faculty";
        String facultyColor = "test color";
        FacultyDTO facultyDTO = new FacultyDTO(facultyId, facultyName, facultyColor);

        Long studentId = 1L;

        JSONObject facultyJSON = new JSONObject();
        facultyJSON.put("id", facultyId);
        facultyJSON.put("name", facultyName);
        facultyJSON.put("color", facultyColor);

        Mockito.when(studentService.getFacultyByStudentId(any(Long.class))).thenReturn(facultyDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hogwarts/student/{id}/facultyByStudentId", studentId)
                        .content(facultyJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }

    @Test
    public void testGetStudent() throws Exception {
        List<StudentDTO> students = List.of(
                new StudentDTO(1L, "test1", 11, null, null, null),
                new StudentDTO(2L, "test2", 22, null, null, null),
                new StudentDTO(3L, "test3", 33, null, null, null)
        );
        Mockito.when(studentService.getAllStudent()).thenReturn(students);
        String studentJson = objectMapper.writeValueAsString(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hogwarts/student")
                        .content(studentJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void testPutStudent() throws Exception {
        StudentDTO studentDTO = new StudentDTO(1L, "test", 11, null, null, null);

        Long id = 2L;
        String name = "test2";
        int age = 22;
        StudentDTO updatedStudentDTO = new StudentDTO(id, name, age, null, null, null);

        JSONObject studentJSON = new JSONObject();
        studentJSON.put("id", id);
        studentJSON.put("name", name);
        studentJSON.put("age", age);

        Mockito.when(studentService.updateStudent(any(StudentDTO.class))).thenReturn(updatedStudentDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/hogwarts/student", updatedStudentDTO)
                        .content(studentJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Long id = 1L;
        Mockito.doNothing().when(studentService).deleteStudent(id);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/hogwarts/student/{id}", id)
        )
                .andExpect(status().isOk());
    }


}
