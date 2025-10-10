package ru.hogwarts.school.WebMvcTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
@AutoConfigureMockMvc
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private Long id;
    private String name;
    private String color;
    private FacultyDTO facultyDTO;
    private JSONObject facultyJSON;

    @BeforeEach
    public void setUp() throws JSONException {
        id = 1L;
        name = "test faculty";
        color = "test color";
        facultyDTO = new FacultyDTO(id, name, color);

        facultyJSON = new JSONObject();
        facultyJSON.put("id", id);
        facultyJSON.put("name", name);
        facultyJSON.put("color", color);
    }

    @Test
    public void testAddFaculty() throws Exception {
        when(facultyService.createFaculty(any(FacultyDTO.class))).thenReturn(facultyDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/hogwarts/faculty")
                        .content(facultyJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testGetFacultyById() throws Exception {
        when(facultyService.readFaculty(any(Long.class))).thenReturn(facultyDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hogwarts/faculty/{id}", id)
                        .content(facultyJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testGetStudentsOfFacultyById() throws Exception {
        List<StudentDTO> students = List.of(
                new StudentDTO(1L, "test1", 11, id, name, color),
                new StudentDTO(2L, "test2", 22, id, name, color),
                new StudentDTO(3L, "test3", 33, id, name, color)
        );
        when(facultyService.getStudentsByFacultyId(any(Long.class))).thenReturn(students);
        String studentJSON = objectMapper.writeValueAsString(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hogwarts/faculty/{id}/studentsByFacultyId", id)
                        .content(studentJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("test2"));
    }

    @Test
    public void testGetFaculty() throws Exception {
        Long id2 = 2L;
        String name2 = "test faculty 2";
        String color2 = "test color 2";
        FacultyDTO facultyDTO2 = new FacultyDTO(id2, name2, color2);
        List<FacultyDTO> faculties = List.of(
                facultyDTO, facultyDTO2
        );
        when(facultyService.getAllFaculty()).thenReturn(faculties);
        String facultiesJSON = objectMapper.writeValueAsString(faculties);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hogwarts/faculty")
                        .content(facultiesJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(faculties.size())))
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[1].name").value(name2));
    }

    @Test
    public void testGetStudentByColor() throws Exception {
        List<StudentDTO> students = List.of(
                new StudentDTO(1L, "test1", 11, id, name, color),
                new StudentDTO(2L, "test2", 22, id, name, color),
                new StudentDTO(3L, "test3", 33, id, name, color)
        );
        when(facultyService.getStudentsByFacultyColor(any(String.class))).thenReturn(students);
        String studentsJSON = objectMapper.writeValueAsString(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hogwarts/faculty/studentsByColor/{color}", color)
                        .content(studentsJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(students.size())))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("test2"));
    }

    @Test
    public void testPutFaculty() throws Exception {
        Long newId = 2L;
        String newName = "new name";
        String newColor = "new color";
        FacultyDTO updatedFacultyDto = new FacultyDTO(newId, newName, newColor);

        JSONObject updatedFacultyDtoJSON = new JSONObject();
        updatedFacultyDtoJSON.put("id", newId);
        updatedFacultyDtoJSON.put("name", newName);
        updatedFacultyDtoJSON.put("color", newColor);

        when(facultyService.updateFaculty(any(FacultyDTO.class))).thenReturn(updatedFacultyDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/hogwarts/faculty", updatedFacultyDto)
                        .content(updatedFacultyDtoJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.id").value(newId)))
                .andExpect((jsonPath("$.name").value(newName)))
                .andExpect((jsonPath("$.color").value(newColor)));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Long id = 1L;
        Mockito.doNothing().when(facultyService).deleteFaculty(id);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/hogwarts/faculty/{id}", id)
                )
                .andExpect(status().isOk());
    }
}
