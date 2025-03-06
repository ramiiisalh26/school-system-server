package com.example.school.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.school.address.Address;
import com.example.school.address.AddressDTO;
import com.example.school.address.AddressMapper;
import com.example.school.address.IAddressServices;
import com.example.school.classes.ClassesDTO;
import com.example.school.classes.IClassesServices;
import com.example.school.security.token.ITokenServices;
import com.example.school.subjects.ISubjectsServices;
import com.example.school.subjects.SubjectsDTO;
import com.example.school.user.IUserServices;
import com.example.school.user.Role;
import com.example.school.user.User;

@Service
public class TeacherServicesImpl implements ITeacherServices{

    private ITeacherRepositry IteacherRepositry;
    private IAddressServices IaddressServices;
    private ISubjectsServices IsubjectsServices;
    private IClassesServices IclassesServices;
    private IUserServices IuserServices;
    private PasswordEncoder passwordEncoder;
    private ITokenServices ItokenServices;

    @Autowired
    public TeacherServicesImpl(
        final ITeacherRepositry IteacherRepositry,
        final IAddressServices IaddressServices,
        final ISubjectsServices IsubjectsServices,
        final IClassesServices IclassesServices,
        final IUserServices IuserServices,
        final PasswordEncoder passwordEncoder,
        final ITokenServices ItokenServices){

        this.IteacherRepositry = IteacherRepositry;
        this.IaddressServices = IaddressServices;
        this.IsubjectsServices = IsubjectsServices;
        this.IclassesServices = IclassesServices;
        this.IuserServices = IuserServices;
        this.ItokenServices = ItokenServices;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean isExists(TeacherDTO teacherDTO) {
        return IteacherRepositry.existsById(teacherDTO.getId());
    }

    @Override
    public List<TeacherDTO> addManyTeacher(List<TeacherDTO> teachersDTO) {
        for (TeacherDTO teacherDTO : teachersDTO) {
            addTeacher(teacherDTO);
        }
        return teachersDTO;
    }

    @Override
    public TeacherDTO addTeacher(TeacherDTO teacherDTO) {
        
        if (teacherDTO == null) {
            throw new RuntimeException("Teacher must be provided");
        }

        User user = User.builder()
            .first_name(teacherDTO.getFirst_name())
            .last_name(teacherDTO.getLast_name())
            .username(teacherDTO.getEmail())
            .password(passwordEncoder.encode("1111"))
            .role(Role.TEACHER)
            .build();

        User savedUser = IuserServices.signUp(user);
        Address address = AddressMapper.fromDTOToEntity(teacherDTO.getAddress());
        address.setUser(savedUser);
        AddressDTO savedAddress = IaddressServices.addAddress(AddressMapper.fromEntityToDTO(address));

        teacherDTO.setAddress(savedAddress);

        List<SubjectsDTO> subjectsDTOs = new ArrayList<>();

        for (SubjectsDTO sub : teacherDTO.getSubjects()) {
            SubjectsDTO subjectsDTO = IsubjectsServices.getSubjectByName(sub.getName());
            subjectsDTOs.add(subjectsDTO);
        }

        teacherDTO.setSubjects(subjectsDTOs);

        List<ClassesDTO> classesList = new ArrayList<>();
        for (ClassesDTO classes : teacherDTO.getClasses()) {
            ClassesDTO classesDTO = IclassesServices.getClassesByName(classes.getName());
            classesList.add(classesDTO);
        }
        teacherDTO.setClasses(classesList);
        
        Teacher teacher = TeacherMapper.fromDTOToEntity(teacherDTO);

        Teacher savedTeacher = IteacherRepositry.save(teacher);

        return TeacherMapper.fromEntityToDTO(savedTeacher);
    }

    @Override
    public Optional<TeacherDTO> getTeacherById(Long id) {
        Optional<Teacher> teacher = IteacherRepositry.findById(id);
        if (teacher.isPresent()) {
            return Optional.of(TeacherMapper.fromEntityToDTO(teacher.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<TeacherDTO> getAllTeacher() {
        List<Teacher> teachers = IteacherRepositry.findAll();
        List<TeacherDTO> teachersDTO = teachers.stream().map(teacher -> TeacherMapper.fromEntityToDTO(teacher)).collect(Collectors.toList());
        System.out.println(teachersDTO);
        return teachersDTO;
    }

    @Override
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher teacher = IteacherRepositry.findById(id).orElseThrow();
        AddressDTO address = IaddressServices.getAddressById(teacher.getAddress().getId()).orElseThrow();
        List<ClassesDTO> classes = IclassesServices.getAllClasses();
        List<SubjectsDTO> subjects = IsubjectsServices.getAllSubjects();
        System.out.println(teacherDTO.getClasses().size());
        int idx = 0;
        if (!classes.isEmpty()) {
            for (int i = 0; i < classes.size(); i++) {
                if (classes.get(i).getId() == teacherDTO.getClasses().get(idx).getId()) {
                    System.out.println("World");
                    classes.get(i).setCapacity(teacherDTO.getClasses().get(idx).getCapacity());
                    classes.get(i).setGrade(teacherDTO.getClasses().get(idx).getGrade());
                    classes.get(i).setSuper_visor(teacherDTO.getClasses().get(i).getSuper_visor());
                    IclassesServices.createClass(classes.get(i));
                    idx++;
                }
            }    
        }
        idx = 0;
        if (!subjects.isEmpty()) {
            for (int i = 0; i < subjects.size(); i++) {
                if (subjects.get(i).getId() == teacherDTO.getSubjects().get(idx).getId()) {
                    subjects.get(i).setName(teacherDTO.getSubjects().get(idx).getName());
                    IsubjectsServices.addSubject(subjects.get(idx));
                    idx = 0;
                }
            }
        }

        if (address != null) {
            address.setCity(teacherDTO.getAddress().getCity());
            address.setCountry(teacherDTO.getAddress().getCountry());
            address.setState(teacherDTO.getAddress().getState());
            address.setStreet(teacherDTO.getAddress().getStreet());
            address.setZip(teacherDTO.getAddress().getZip());
            IaddressServices.addAddress(address);
        }

        if (teacher != null) {
            teacher.setFirst_name(teacherDTO.getFirst_name());
            teacher.setMiddle_name(teacherDTO.getMiddle_name());
            teacher.setLast_name(teacher.getLast_name());
            teacher.setPhone(teacherDTO.getPhone());
            teacher.setPhoto(teacherDTO.getPhoto());
            teacher.setTeacher_id(teacherDTO.getTeacher_id());
            teacher.setEmail(teacherDTO.getEmail());
            IteacherRepositry.save(teacher);
        }

        return TeacherMapper.fromEntityToDTO(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = IteacherRepositry.findById(id).get();
        
        teacher.getClasses().forEach(cla ->{
            Long idClass = cla.getId();
            cla.setTeacher(null);
            IclassesServices.setTeacherIdToBeNull(idClass);
        });

        teacher.getSubjects().forEach(sub -> {
            sub.setTeachers(null);
        });

        Long idAddress = teacher.getAddress().getId();
        Long idUser = teacher.getAddress().getUser().getId();
        
        IaddressServices.setUserIdToBeNull(idUser);
        IteacherRepositry.setAddressIdToBeNull(idAddress);

        IaddressServices.deleteAddressById(idAddress);
        
        teacher.setAddress(null);

        IuserServices.deleteUserById(idUser);

        if (!ItokenServices.findAllToken(idUser).isEmpty()) {
            ItokenServices.deleteAllTokenByUserId(idUser);            
        }


        IteacherRepositry.save(teacher);
        IteacherRepositry.deleteById(id);
    }


    @Override
    public TeacherDTO addTeacherSubjects() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTeacherSubjects'");
    }
    
}
