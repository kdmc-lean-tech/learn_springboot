package com.demo.demo.Modules.Student.Controllers;

import com.demo.demo.Modules.Student.Models.Student;
import com.demo.demo.Modules.Student.Services.StudentService;
import com.demo.demo.Shared.Helpers.Paginate;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/student")
public record StudentController(StudentService studentService) {

    @GetMapping
    public List<Student> getStudents(
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        // return this.studentService.getStudents(new Paginate(page, pageSize));
        return null;
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student student) {
        this.studentService.createStudent(student);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity deleteStudent(@PathVariable("studentId") Long studentId) throws NotFoundException {
        this.studentService.deleteStudentById(studentId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{studentId}")
    public Optional<Student> getStudent(@PathVariable("studentId") Long studentId) throws NotFoundException {
        return this.studentService.getStudentById(studentId);
    }

    @PutMapping("{studentId}")
    public ResponseEntity updateUser(
            @RequestBody() Student student, @PathVariable("studentId") Long studentId) throws NotFoundException {
        this.studentService.updateStudent(student, studentId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/grouping")
    public Map<Integer, List<Student>> getStudentsGroupingByAge() {
        return this.studentService.groupingStudentsByAge();
    }
}
