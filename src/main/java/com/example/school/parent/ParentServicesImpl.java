package com.example.school.parent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.school.address.*;
import com.example.school.security.token.ITokenServices;
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
    private final ITokenServices ItokenServices;
    @Autowired
    public ParentServicesImpl(
            final IParentRepositry IparentRepositry,
            final IStudentRepositry IstudentRepositry,
            final IAddressRespositry IaddressRepositry,
            final IUserServices IuserServices,
            final PasswordEncoder passwordEncoder,
            final ITokenServices ItokenServices
    ) {
        this.IparentRepositry = IparentRepositry;
        this.IstudentRepositry = IstudentRepositry;
        this.IaddressRepositry = IaddressRepositry;
        this.IuserServices = IuserServices;
        this.passwordEncoder = passwordEncoder;
        this.ItokenServices = ItokenServices;
    }

    @Override
    public Boolean isExists(ParentDTO parentDTO) {
        return IparentRepositry.existsById(parentDTO.getId());
    }

    @Override
    public List<ParentDTO> getAllParent() {
        List<Parent> parents = IparentRepositry.findAll();
//        System.out.println(parents.toString());
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

        Parent parent = Parent.builder()
                .first_name(parentDTO.getFirst_name())
                .middle_name(parentDTO.getMiddle_name())
                .last_name(parentDTO.getLast_name())
                .email(parentDTO.getEmail())
                .phone(parentDTO.getPhone())
                .address(savedAddress)
                .build();

        IparentRepositry.save(parent);
    }

    @Override
    public ParentDTO updateParent(Long id, ParentDTO parentDTO) {
        Parent parent = IparentRepositry.findById(id).orElseThrow();

        Address address = IaddressRepositry.findById(parent.getAddress().getId()).orElseThrow();

        parent.setFirst_name(parentDTO.getFirst_name());
        parent.setMiddle_name(parentDTO.getMiddle_name());
        parent.setLast_name(parentDTO.getLast_name());
        parent.setPhone(parentDTO.getPhone());

//        for (int i = 0; i < students.size(); i++) {
//            if (students.get(i).getStudent_id().equals(parentDTO.getStudents().get(i).getStudent_id())) {
//                students.get(i).setEmail(parentDTO.getStudents().get(i).getEmail() == null ? parent.getStudent().get(i).getEmail() : parentDTO.getStudents().get(i).getEmail());
//                students.get(i).setPhone(parentDTO.getStudents().get(i).getPhone() == null ? parent.getStudent().get(i).getPhone() : parentDTO.getStudents().get(i).getPhone());
//                students.get(i).setPhoto(parentDTO.getStudents().get(i).getPhoto() == null ? parent.getStudent().get(i).getPhoto() : parentDTO.getStudents().get(i).getPhoto());
//                students.get(i).setFirst_name(parentDTO.getStudents().get(i).getFirst_name() == null ? parent.getStudent().get(i).getFirst_name() : parentDTO.getStudents().get(i).getFirst_name());
//                students.get(i).setMiddle_name(parentDTO.getStudents().get(i).getMiddle_name() == null ? parent.getStudent().get(i).getMiddle_name() : parentDTO.getStudents().get(i).getMiddle_name());
//                students.get(i).setLast_name(parentDTO.getStudents().get(i).getLast_name() == null ? parent.getStudent().get(i).getLast_name() : parentDTO.getStudents().get(i).getLast_name());
//            }
//        }

//        parent.setStudent(students);

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
        Parent parent = IparentRepositry.findById(id).orElseThrow();

        parent.getStudent().forEach(student -> student.setParents(null));

        Long idAddress = parent.getAddress().getId();
        Long idUser = parent.getAddress().getUser().getId();

        parent.setAddress(null);

        IaddressRepositry.deleteById(idAddress);
        IuserServices.deleteUserById(idUser);

        if (!ItokenServices.findAllToken(idUser).isEmpty()) {
            ItokenServices.deleteAllTokenByUserId(idUser);
        }

        IparentRepositry.deleteById(id);
    }


}
