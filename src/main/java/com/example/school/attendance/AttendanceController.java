package com.example.school.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/attendance")
public class AttendanceController {

    private final IAttendanceServices IattendanceService;

    @Autowired
    public AttendanceController(IAttendanceServices IattendanceService){
        this.IattendanceService = IattendanceService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<AttendanceDTO>> addAttendance(@RequestBody final List<AttendanceDTO> attendance){
        final List<AttendanceDTO> savedAttendance = IattendanceService.addManyAttendance(attendance);
        return new ResponseEntity<>(attendance, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable final Long id){
        AttendanceDTO attendance = IattendanceService.getAttendanceById(id);
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance(){
        return new ResponseEntity<>(IattendanceService.getAllAttendance(), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable final Long id, @RequestBody final AttendanceDTO attendance){
        IattendanceService.updateAttendance(id, attendance);
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<AttendanceDTO> deleteAttendanceById(@PathVariable final Long id){
        IattendanceService.deleteAttendanceById(id);
        return new ResponseEntity<>(IattendanceService.getAttendanceById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/getAllAttendanceByCourse/{course_code}")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendanceByCourse(@PathVariable final String course_code){
        return new ResponseEntity<>(IattendanceService.getAllAttendanceByCourse(course_code), HttpStatus.OK);
    }

    @GetMapping(path = "/getAllAttendanceByStudent/{student_code}")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendanceByStudent(@PathVariable final String student_code){
        return new ResponseEntity<>(IattendanceService.getAllAttendanceByStudent(student_code), HttpStatus.OK);
    }

    @GetMapping(path = "/getAttendancesByDate/{date}")
    public ResponseEntity<List<AttendanceDTO>> getAttendancesByDate(@PathVariable final String date){
        return new ResponseEntity<>(IattendanceService.getAttendancesByDate(date), HttpStatus.OK);
    }

    @GetMapping(path = "/getAttendancesFromToday/{from}/{to}/{PRESENT}")
    public ResponseEntity<List<AttendanceDTO>> getAttendancesFromToday(@PathVariable final String from, @PathVariable final String to, @PathVariable final String PRESENT){
        return new ResponseEntity<>(IattendanceService.getAttendancesFromToday(from,to,PRESENT), HttpStatus.OK);
    }

}
