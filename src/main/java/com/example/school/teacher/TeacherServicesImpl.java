package com.example.school.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.school.address.Address;
import com.example.school.address.IAddressRespositry;
import com.example.school.classes.*;
import com.example.school.courses.Courses;
import com.example.school.courses.CoursesDTO;
import com.example.school.courses.CoursesMapper;
import com.example.school.courses.ICoursesRepositry;
import com.example.school.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.school.security.token.ITokenServices;

@Service
public class TeacherServicesImpl implements ITeacherServices{

    private final ITeacherRepositry IteacherRepositry;
    private final IAddressRespositry IaddressRepositry;
    private final ICoursesRepositry IcoursesRepositry;
    private final IClassesRepositry IclassesRepositry;
    private final IUserServices IuserServices;
    private final PasswordEncoder passwordEncoder;
    private final ITokenServices ItokenServices;

    @Autowired
    public TeacherServicesImpl(
        final ITeacherRepositry IteacherRepositry,
        final IAddressRespositry IaddressRepositry,
        final ICoursesRepositry IcoursesRepositry,
        final IClassesRepositry IclassesRepositry,
        final IUserServices IuserServices,
        final PasswordEncoder passwordEncoder,
        final ITokenServices ItokenServices){

        this.IteacherRepositry = IteacherRepositry;
        this.IaddressRepositry = IaddressRepositry;
        this.IcoursesRepositry = IcoursesRepositry;
        this.IclassesRepositry = IclassesRepositry;
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
    public void addTeacher(TeacherDTO teacherDTO) {
        
        if (teacherDTO == null) {
            throw new RuntimeException("Teacher must be provided");
        }

        Teacher teacher = TeacherMapper.fromDTOToEntity(teacherDTO);

        User user = User.builder()
            .first_name(teacherDTO.getFirst_name())
            .last_name(teacherDTO.getLast_name())
            .username(teacherDTO.getEmail())
            .password(passwordEncoder.encode("1111"))
            .role(Role.TEACHER)
            .build();

        User savedUser = IuserServices.signUp(user);

        Address address = Address.builder()
                .country(teacherDTO.getAddress().getCountry())
                .city(teacherDTO.getAddress().getCity())
                .street(teacherDTO.getAddress().getStreet())
                .zip(teacherDTO.getAddress().getZip())
                .state(teacherDTO.getAddress().getState())
                .user(savedUser)
                .build();

        Address savedAddress = IaddressRepositry.save(address);

        teacher.setAddress(savedAddress);

        List<Courses> courses = new ArrayList<>();

        for (Courses sub : teacher.getCourses()) {
            Courses subject = IcoursesRepositry.getCourseByName(sub.getName());
            courses.add(subject);
        }

        teacher.setCourses(courses);

        List<Classes> classesList = new ArrayList<>();
        for (Classes classes : teacher.getClasses()) {
            Classes classed = IclassesRepositry.getClassesByName(classes.getName());
            classesList.add(classed);
        }
        teacher.setClasses(classesList);

        Teacher savedTeacher = IteacherRepositry.save(teacher);
    }

    @Override
    public Optional<TeacherDTO> getTeacherById(Long id) {
        Optional<Teacher> teacher = IteacherRepositry.findById(id);
        return teacher.map(TeacherMapper::fromEntityToDTO);
    }

    @Override
    public List<TeacherDTO> getAllTeacher() {
        List<Teacher> teachers = IteacherRepositry.findAll();
        List<TeacherDTO> teachersDTO = teachers.stream().map(TeacherMapper::fromEntityToDTO).collect(Collectors.toList());
        System.out.println(teachersDTO);
        return teachersDTO;
    }

    @Override
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher teacher = IteacherRepositry.findById(id).orElseThrow();

        Address gterAddress = IaddressRepositry.findById(teacherDTO.getAddress().getId()).get();

//        teacher.setClasses(teacherDTO.getClasses().stream().map(ClassesMapper::fromDTOToEntity).collect(Collectors.toList()));
        teacher.setCourses(teacherDTO.getCourses().stream().map(CoursesMapper::fromDTOToEntity).collect(Collectors.toList()));

        Address address = Address.builder()
                .state(teacherDTO.getAddress().getState())
                .country(teacherDTO.getAddress().getCountry())
                .city(teacherDTO.getAddress().getCity())
                .street(teacherDTO.getAddress().getStreet())
                .zip(teacherDTO.getAddress().getZip())
                .build();

        teacher.setAddress(address);

        teacher.setFirst_name(teacherDTO.getFirst_name());
        teacher.setMiddle_name(teacherDTO.getMiddle_name());
        teacher.setLast_name(teacher.getLast_name());
        teacher.setPhone(teacherDTO.getPhone());
        teacher.setPhoto(teacherDTO.getPhoto());
        teacher.setTeacher_id(teacherDTO.getTeacher_id());
        teacher.setEmail(teacherDTO.getEmail());

        IteacherRepositry.save(teacher);

        return TeacherMapper.fromEntityToDTO(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = IteacherRepositry.findById(id).get();
        
        teacher.getClasses().forEach(cla -> cla.setTeacher(null));

        teacher.getCourses().forEach(sub -> sub.setTeachers(null));

        teacher.getResult().forEach(re -> re.setTeacher(null));

        Long idAddress = teacher.getAddress().getId();
        Long idUser = teacher.getAddress().getUser().getId();

//        teacher.setAddress(null);

        if (idAddress != null) {
            teacher.setAddress(null);
            IaddressRepositry.deleteById(idAddress);
        }

        if (idUser != null) {
            IuserServices.deleteUserById(idUser);
        }


        if (!ItokenServices.findAllToken(idUser).isEmpty()) {
            ItokenServices.deleteAllTokenByUserId(idUser);
        }

        IteacherRepositry.deleteById(id);
    }


//    @Override
//    public TeacherDTO addTeacherCourses() {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'addTeacherCourses'");
//    }

    @Override
    public List<CoursesDTO> getTeacherCourses(Long id){
        List<Courses> Courses = IcoursesRepositry.getAllCoursesByTeacher_id(id);
        return  Courses.stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClassesDTO> getTeacherClasses(Long id){
        List<Classes> classes = IclassesRepositry.getClassesByTeacher_id(id);
        return  classes.stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList());
    }
    
}
