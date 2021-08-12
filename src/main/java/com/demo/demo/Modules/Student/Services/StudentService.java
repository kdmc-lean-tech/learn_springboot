package com.demo.demo.Modules.Student.Services;

import com.demo.demo.Modules.Student.Models.Student;
import com.demo.demo.Modules.Student.Repositories.StudentRepository;
import com.demo.demo.Shared.Helpers.Paginate;
import com.sun.jdi.InternalException;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(Paginate paginate) {
        return this.studentRepository.findAll().stream()
                                        .skip(paginate.getOffset())
                                        .limit(paginate.getPageSize())
                                        .collect(Collectors.toList());
    }

    public void createStudent(Student student) {
        Optional<Student> studentToFind = this.studentRepository
                .getStudentByEmail(student.getEmail());
        if (studentToFind.isPresent()) {
            throw new InternalException("The user with email " +  student.getEmail() + " already exists.");
        }
        this.studentRepository.save(student);
    }

    public Optional<Student> getStudentById(long studentId) throws NotFoundException {
        boolean existStudent = this.studentRepository.existsById(studentId);
        if (!existStudent) {
            throw new NotFoundException("The user with id" + studentId + " no exits.");
        }
        return this.studentRepository.findById(studentId);
    }

    public void deleteStudentById(long studentId) throws NotFoundException {
        boolean existStudent = this.studentRepository.existsById(studentId);
        if (!existStudent) {
            throw new NotFoundException("The user with id " + studentId + " not exits.");
        }
        this.studentRepository.deleteById(studentId);
    }

    public void updateStudent(Student student, Long studentId) throws NotFoundException {
        boolean existStudent = this.studentRepository.existsById(studentId);
        if (!existStudent) {
            throw new NotFoundException("The user with id " + studentId + " not exits.");
        }
        this.studentRepository.findById(studentId).map(model -> {
            model.setName(student.getName());
            model.setBirthDate(student.getBirthDate());
            return model;
        });
    }

    public Map<Integer, List<Student>> groupingStudentsByAge() {
        return this.studentRepository.findAll().stream()
                .collect(Collectors.groupingBy(Student::getAge));
    }
}
