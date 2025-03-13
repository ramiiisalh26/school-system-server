package com.example.school.parent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.school.address.*;
import com.example.school.student.*;
import com.example.school.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ParentServicesImpl implements IParentServices {


    private final IParentRepositry IparentRepositry;
    private final IStudentRepositry IstudentRepositry;
    private final IAddressRespositry IaddressRepositry;
    private final IUserServices IuserServices;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ParentServicesImpl(
            final IParentRepositry IparentRepositry,
            final IStudentRepositry IstudentRepositry,
            final IAddressRespositry IaddressRepositry,
            final IUserServices IuserServices,
            final PasswordEncoder passwordEncoder
    ) {
        this.IparentRepositry = IparentRepositry;
        this.IstudentRepositry = IstudentRepositry;
        this.IaddressRepositry = IaddressRepositry;
        this.IuserServices = IuserServices;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean isExists(ParentDTO parentDTO) {
        return IparentRepositry.existsById(parentDTO.getId());
    }

    @Override
    public List<ParentDTO> getAllParent() {
        List<Parent> parents = IparentRepositry.findAll();
        return parents.stream().map(ParentMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ParentDTO> addManyParent(List<ParentDTO> parentsDTO) throws Exception {
        for (ParentDTO parentDTO : parentsDTO) {
            addParent(parentDTO);
        }
        return parentsDTO;
    }


    @Override
    public void addParent(ParentDTO parentDTO) throws Exception {

        if (parentDTO == null) {
            throw new RuntimeException("Must be provided parent");
        }

        User user = User.builder()
                .first_name(parentDTO.getFirst_name())
                .last_name(parentDTO.getLast_name())
                .username(parentDTO.getEmail())
                .password(passwordEncoder.encode("5555"))
                .role(Role.PARENT)
                .build();

        System.out.println(user);
        User savedUser = IuserServices.signUp(user);

        Address address = Address.builder()
                .city(parentDTO.getAddress().getCity())
                .state(parentDTO.getAddress().getState())
                .country(parentDTO.getAddress().getCountry())
                .street(parentDTO.getAddress().getStreet())
                .zip(parentDTO.getAddress().getZip())
                .user(savedUser)
                .build();

        Address savedAddress = IaddressRepositry.save(address);

        List<Student> students = parentDTO.getStudents().stream()
                .map(student -> IstudentRepositry.FindByStudentID(student.getStudent_id()))
                .toList();

        Parent parent = Parent.builder()
                .first_name(parentDTO.getFirst_name())
                .middle_name(parentDTO.getMiddle_name())
                .last_name(parentDTO.getLast_name())
                .email(parentDTO.getEmail())
                .phone(parentDTO.getPhone())
                .address(savedAddress)
                .student(students)
                .build();

        IparentRepositry.save(parent);
    }

    @Override
    public ParentDTO updateParent(Long id, ParentDTO parentDTO) {

        Parent parent = IparentRepositry.findById(id).orElseThrow();
        List<Student> students = IparentRepositry.getStudentByParentId(parent.getId());
        Address address = IaddressRepositry.findById(parent.getAddress().getId()).orElseThrow();

        parent.setFirst_name(parentDTO.getFirst_name());
        parent.setMiddle_name(parentDTO.getMiddle_name());
        parent.setLast_name(parentDTO.getLast_name());
        parent.setEmail(parentDTO.getEmail());
        parent.setPhone(parentDTO.getPhone());

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudent_id().equals(parentDTO.getStudents().get(i).getStudent_id())) {
                students.get(i).setEmail(parentDTO.getStudents().get(i).getEmail());
                students.get(i).setPhone(parentDTO.getStudents().get(i).getPhone());
                students.get(i).setPhoto(parentDTO.getStudents().get(i).getPhoto());
            }
        }

        parent.setStudent(students);

        address.setCity(parentDTO.getAddress().getCity());
        address.setCountry(parentDTO.getAddress().getCountry());
        address.setState(parentDTO.getAddress().getState());
        address.setStreet(parentDTO.getAddress().getStreet());
        address.setZip(parentDTO.getAddress().getZip());

        parent.setAddress(address);

        IparentRepositry.save(parent);

        return ParentMapper.fromEntityToDTO(parent);
    }

    @Override
    public Optional<ParentDTO> getParentById(Long Id) {
        Optional<Parent> parent = IparentRepositry.findById(Id);
        return parent.map(ParentMapper::fromEntityToDTO);
    }

    @Override
    public void deleteById(Long id) {
        IparentRepositry.deleteById(id);
    }

    @Override
    public List<StudentDTO> getStudentByParentId(Long id) {
        List<Student> students = IparentRepositry.getStudentByParentId(id);
        return students.stream().map(StudentMapper::fromEntityToDTO).collect(Collectors.toList());
    }

}
