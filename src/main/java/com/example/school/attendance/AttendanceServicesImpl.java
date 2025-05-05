package com.example.school.attendance;

import com.example.school.courses.Courses;
import com.example.school.courses.ICoursesRepositry;
import com.example.school.student.IStudentRepositry;
import com.example.school.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServicesImpl implements IAttendanceServices{

    private final IAttendanceRepositry IattendanceRepositry;
    private final IStudentRepositry IstudentRepositry;
    private final ICoursesRepositry IcoursesRepositry;

    @Autowired
    public AttendanceServicesImpl(
            IAttendanceRepositry IattendanceRepositry,
            IStudentRepositry IstudentRepositry,
            ICoursesRepositry IcoursesRepositry
    ) {
        this.IattendanceRepositry = IattendanceRepositry;
        this.IstudentRepositry = IstudentRepositry;
        this.IcoursesRepositry = IcoursesRepositry;
    }

    public Boolean isExist(AttendanceDTO attendance) {
        return IattendanceRepositry.existsById(attendance.getId());
    }

    @Override
    public List<AttendanceDTO> addManyAttendance(List<AttendanceDTO> attendance) {
        attendance.forEach(this::addAttendance);
        return attendance;
    }

    @Override
    public void addAttendance(AttendanceDTO attendance) {

        if (attendance == null) {
            throw new NullPointerException("attendance is null");
        }

        Student student = IstudentRepositry.getByStudentCode(attendance.getStudent_code());
        if (student == null) {
            throw new NullPointerException("student is null");
        }
        Courses courses = IcoursesRepositry.getCourseByCourse_code(attendance.getCourse_code());
        if (courses == null) {
            throw new NullPointerException("courses is null");
        }

        Attendance savedAttendance = Attendance.builder()
                .student(student)
                .courses(courses)
                .date(LocalDateTime.now())
                .status(StatusOfAtte.valueOf(attendance.getStatus()))
                .build();

        IattendanceRepositry.save(savedAttendance);
    }

    @Override
    public AttendanceDTO getAttendanceById(Long id) {
        return AttendanceMapper.fromEntityToDTO(IattendanceRepositry.getAttendanceById(id));
    }

    @Override
    public List<AttendanceDTO> getAllAttendance() {
        return IattendanceRepositry.findAll().stream().map(AttendanceMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public void updateAttendance(Long id, AttendanceDTO attendance) {
    }

    @Override
    public List<AttendanceDTO> getAllAttendanceByCourse(String course_code) {
        return IattendanceRepositry.getAttendancesByCoursesCode(course_code).stream().map(AttendanceMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAllAttendanceByStudent(String student_code) {
        return IattendanceRepositry.getAttendanceByStudentCode(student_code).stream().map(AttendanceMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAttendancesFromToday(String from, String to, String status) {
        LocalDate localDateFrom = LocalDate.parse(from);
        LocalDate localDateTo = LocalDate.parse(to);
        return IattendanceRepositry.getAttendancesFromToday(localDateFrom,localDateTo,status).stream().map(AttendanceMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAttendancesByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return IattendanceRepositry.getAttendancesByDate(localDate).stream().map(AttendanceMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAttendanceById(Long attendance_id) {
        IattendanceRepositry.deleteById(attendance_id);
    }
}
