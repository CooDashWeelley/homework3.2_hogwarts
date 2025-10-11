package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.specificRequest.AverageAge;
import ru.hogwarts.school.repository.specificRequest.NumberOfStudents;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "select count(student.id) from student", nativeQuery = true)
    List<NumberOfStudents> getNumberOfStudents();

    @Query(value = "select avg(student.age) from student", nativeQuery = true)
    List<AverageAge> getAverageAge();

    @Query(value = "select * from student order by id desc limit 5 ", nativeQuery = true)
    List<Student> getLastFiveStudent();
}
