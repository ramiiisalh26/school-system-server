package com.example.school.student;

import java.time.Year;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.example.school.address.*;
import com.example.school.assignment.IAssignmentRepository;
import com.example.school.classes.*;
//import com.example.school.security.cache.RedisStudentCacheService;
import com.example.school.parent.IParentRepositry;
import com.example.school.result.IResultRepositry;
import com.example.school.security.token.ITokenServices;
import com.example.school.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentServicesImpl implements IStudentServices{

    private final IStudentRepositry IstudentRepositry;
    private final IAddressRespositry IaddressRepositry;
    private final IUserServices IuserServices;
    private final PasswordEncoder passwordEncoder;
    private final IClassesRepositry IclassesRepositry;
    private final ITokenServices ItokenServices;
    private final IParentRepositry IparentRepositry;
    private final IResultRepositry IresultRepositry;
    private final IAssignmentRepository IassignmentRepository;
//    private final RedisStudentCacheService redisStudentCacheService;

    @Autowired
    public StudentServicesImpl(
        final IStudentRepositry IstudentRepositry,
        final IAddressRespositry IaddressRepositry,
        final IUserServices IuserServices,
        final PasswordEncoder passwordEncoder,
        final IClassesRepositry IclassesRepositry,
        final ITokenServices ItokenServices,
        final IResultRepositry IresultRepositry,
        final IAssignmentRepository IassignmentRepositry,
        final IParentRepositry IparentRepositry
//        final RedisStudentCacheService redisStudentCacheService
    ){
        this.IstudentRepositry = IstudentRepositry;
        this.IaddressRepositry = IaddressRepositry;
        this.IuserServices = IuserServices;
        this.passwordEncoder = passwordEncoder;
        this.IclassesRepositry = IclassesRepositry;
        this.ItokenServices = ItokenServices;
        this.IresultRepositry = IresultRepositry;
        this.IassignmentRepository = IassignmentRepositry;
        this.IparentRepositry = IparentRepositry;
//        this.redisStudentCacheService = redisStudentCacheService;
    }
    
    @Override
    public Boolean isExists(StudentDTO studentDTO) {
        return IstudentRepositry.existsById(studentDTO.getId());
    }

    @Override
    public List<StudentDTO> addManyStudent(List<StudentDTO> studentsDTO) {
        for (StudentDTO studentDTO : studentsDTO) {
            addStudent(studentDTO);
        }
        return studentsDTO;
    }

    @Override
    public void addStudent(StudentDTO studentDTO) {

        if (studentDTO == null) {
            throw new RuntimeException("Student must be provided");
        }
        System.out.println(studentDTO);
        Student student = StudentMapper.fromDTOToEntity(studentDTO);

        int currentYear = Year.now().getValue();

        String StudentId = generateStudentID(currentYear);

        student.setStudent_id(StudentId);

        User user = User.builder()
                .first_name(studentDTO.getFirst_name())
                .last_name(studentDTO.getLast_name())
                .username(studentDTO.getEmail())
                .password(passwordEncoder.encode("2222"))
                .username(studentDTO.getEmail())
                .role(Role.STUDENT)
                .build();

        Address address = Address.builder()
                .city(student.getAddress().getCity())
                .country(student.getAddress().getCountry())
                .street(student.getAddress().getStreet())
                .zip(student.getAddress().getZip())
                .state(student.getAddress().getState())
                .build();

//        student.setAddress(savedAddress);
//        List<Classes> classes = student.getClasses().stream().map(classed -> IclassesRepositry.getClassesByName(classed.getName())).collect(Collectors.toList());
//        if (classes.isEmpty()) throw new RuntimeException("Classes Must be provided")
//        student.setClasses(classes);
        User savedUser = IuserServices.signUp(user);
        address.setUser(savedUser);
        IaddressRepositry.save(address);
        student.setAddress(address);
        IstudentRepositry.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {

        Student student = IstudentRepositry.findById(id).orElseThrow();

        student.getClasses().forEach((classes) ->{
//            IclassesRepositry.deleteById(classes.getId());
            classes.setStudent(null);
        });

        Long idAddress = student.getAddress().getId();
        Long idUser = student.getAddress().getUser().getId();

        student.getAddress().setUser(null);
        student.setAddress(null);

        IaddressRepositry.deleteById(idAddress);

        IuserServices.deleteUserById(idUser);

        student.getParents().forEach(parent -> {
//            IparentRepositry.deleteById(parent.getId());
            parent.setStudent(null);
        });

        student.getResults().forEach(result ->{
            IresultRepositry.deleteById(result.getId());
            result.setStudent(null);
        });

        student.getAssignments().forEach(assignment ->{
            IassignmentRepository.deleteById(assignment.getId());
            assignment.setStudent(null);
        });

        if (!ItokenServices.findAllToken(id).isEmpty()) {
            ItokenServices.deleteAllTokenByUserId(idUser);
        }

        IstudentRepositry.deleteById(id);
    }

    @Override
    public List<StudentDTO> getAllStudent() {
        List<Student> students = IstudentRepositry.findAll();
        return students.stream().map(StudentMapper::fromEntityToDTO).collect(Collectors.toList());
    }


    @Override
    public Optional<StudentDTO> getStudentById(Long id) {
//        StudentDTO cashedStudent = redisStudentCacheService.getStudentById(id);

//        if (cashedStudent != null){
//            return Optional.of(cashedStudent);
//        }


        Optional<Student> student = IstudentRepositry.findById(id);
//        student.ifPresent(value -> redisStudentCacheService.saveStudent(id, StudentMapper.fromEntityToDTO(value)));

        return student.map(StudentMapper::fromEntityToDTO);
    }

    @Override
    public StudentDTO updateStudentById(Long id, StudentDTO studentDTO) {

        Student student = IstudentRepositry.findById(id).orElseThrow();
        Address address = IaddressRepositry.findById(studentDTO.getAddress().getId()).orElseThrow();

        address.setCity(studentDTO.getAddress().getCity());
        address.setCountry(studentDTO.getAddress().getCountry());
        address.setStreet(studentDTO.getAddress().getStreet());
        address.setZip(studentDTO.getAddress().getZip());
        address.setState(studentDTO.getAddress().getState());
        student.setAddress(address);

//        student.setClasses(studentDTO.getClasses().stream()
//                        .map(classesDTO -> IclassesRepositry.findById(classesDTO.getId())
//                        .orElseThrow(() -> new RuntimeException( "Class with ID " + classesDTO.getId() + " not found")))
//                        .collect(Collectors.toList()));


        student.setFirst_name(studentDTO.getFirst_name());
        student.setMiddle_name(studentDTO.getMiddle_name());
        student.setLast_name(studentDTO.getLast_name());
        student.setEmail(studentDTO.getEmail());
        student.setGrade(studentDTO.getGrade());
        student.setPhone(studentDTO.getPhone());
        student.setPhoto(studentDTO.getPhoto());
        student.setStudent_id(studentDTO.getStudent_id());

        Student savedStudent = IstudentRepositry.save(student);
        return StudentMapper.fromEntityToDTO(savedStudent);
    }


    @Override
    public Optional<StudentDTO> getByStudentCode(String student_code) {
        Optional<Student> student = Optional.ofNullable(IstudentRepositry.getByStudentCode(student_code));
        return student.map(StudentMapper::fromEntityToDTO);
    }

    @Override
    public StudentChartApi getBoysAndGirlsCount() {
        List<String> gendersValue = IstudentRepositry.getBoysAndGirlsCount();
        AtomicInteger boys = new AtomicInteger();
        AtomicInteger girls = new AtomicInteger();
        gendersValue.forEach((gender) -> {
            if (gender.equals("MALE")) {
                boys.getAndIncrement();
            }else {
                girls.getAndIncrement();
            }
       });

        return StudentChartApi.builder()
                .boys_count(boys.get())
                .girls_count(girls.get())
                .total_count(gendersValue.size())
                .build();
    }

    @Override
    public List<StudentDTO> getStudentByParentId(Long id) {
        return IstudentRepositry.getStudentByParentId(id).stream().map(StudentMapper::fromEntityToDTO).collect(Collectors.toList());
    }


    private String generateStudentID(int year) {

        String yearPart = String.valueOf(year).substring(2);

        String lastStudentId = IstudentRepositry.FindLastStudentID(yearPart);

        int newIncrement = 1;
        if (lastStudentId != null) {
            int lastIncrement = Integer.parseInt(lastStudentId.substring(2));
            newIncrement = lastIncrement + newIncrement;
        }

        return  yearPart + String.format("%02d", newIncrement);
    }
}
