package ru.hogwarts.school.dto;

import java.util.Objects;

public class StudentDTO {
    private Long id;
    private String name;
    private int age;
    private Long facultyId;
    private String facultyName;
    private String facultyColor;

    public StudentDTO() {}

    public StudentDTO(Long id, String name, int age, Long facultyId, String facultyName, String facultyColor) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyColor = facultyColor;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", facultyId=" + facultyId +
                ", facultyName='" + facultyName + '\'' +
                ", facultyColor='" + facultyColor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return age == that.age && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(facultyId, that.facultyId) && Objects.equals(facultyName, that.facultyName) && Objects.equals(facultyColor, that.facultyColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, facultyId, facultyName, facultyColor);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyColor() {
        return facultyColor;
    }

    public void setFacultyColor(String facultyColor) {
        this.facultyColor = facultyColor;
    }

}
