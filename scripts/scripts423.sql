select s.name, s.age, f.name from student s full join faculty f on s.faculty = f.id

select s.* from student s right join avatar a on s.id = a.student_id