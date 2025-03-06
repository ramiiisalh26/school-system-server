package com.example.school.student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.school.address.Address;
import com.example.school.address.IAddressRespositry;

@Service
public class StudentServicesImpl implements IStudentServices{

    private IStudentRepositry studentRepositry;
    private IAddressRespositry addressRespositry;

    @Autowired
    public StudentServicesImpl(
        final IStudentRepositry studentRepositry,
        final IAddressRespositry addressRespositry
    ){
        this.studentRepositry = studentRepositry;
        this.addressRespositry = addressRespositry;
    }
    
    @Override
    public Boolean isExists(StudentDTO studentDTO) {
        return studentRepositry.existsById(studentDTO.getId());
    }

    @Override
    public List<StudentDTO> addManyStudent(List<StudentDTO> studentsDTO) {
        for (StudentDTO studentDTO : studentsDTO) {
            addStudent(studentDTO);
        }
        return studentsDTO;
    }

    @Override
    public StudentDTO addStudent(StudentDTO studentDTO) {
        if (studentDTO == null) {
            throw new RuntimeException("Student must be provided");
        }
        Student student = StudentMapper.fromDTOToEntity(studentDTO);

        Student savedStudent = studentRepositry.save(student);

        return StudentMapper.fromEntityToDTO(savedStudent);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepositry.deleteById(id);
    }

    @Override
    public List<StudentDTO> getAllStudent() {
        List<Student> students = studentRepositry.findAll();
        List<StudentDTO> studentsDTO = students.stream().map(student -> StudentMapper.fromEntityToDTO(student)).collect(Collectors.toList());
        return studentsDTO;
    }

    @Override
    public Optional<StudentDTO> getStudentById(Long id) {
        Optional<Student> student = studentRepositry.findById(id);
        if (student.isPresent()) {
            return Optional.of(StudentMapper.fromEntityToDTO(student.get()));
        }

        return Optional.empty();
    }

    @Override
    public StudentDTO updateStudentById(Long id, StudentDTO studentDTO) {
        
        Student student = studentRepositry.findById(id).orElseThrow();
        Address address = addressRespositry.findById(studentDTO.getAddress().getId()).orElseThrow();
        
        if (address != null) {
            address.setCity(studentDTO.getAddress().getCity());
            address.setCountry(studentDTO.getAddress().getCountry());
            address.setState(studentDTO.getAddress().getState());
            address.setStreet(studentDTO.getAddress().getStreet());
            address.setZip(studentDTO.getAddress().getZip());
            addressRespositry.save(address);
        }

        if (student != null) {
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            student.setGrade(studentDTO.getGrade());
            student.setPhone(studentDTO.getPhone());
            student.setPhoto(studentDTO.getPhoto());
            student.setStudent_id(studentDTO.getStudent_id());
            studentRepositry.save(student);
        }
        return StudentMapper.fromEntityToDTO(student);
    }
    
}
