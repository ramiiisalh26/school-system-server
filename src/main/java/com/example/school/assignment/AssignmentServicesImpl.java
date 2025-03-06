package com.example.school.assignment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServicesImpl implements IAssignmentServices{

    private IAssignmentRespositry assignmentRespositry;

    @Autowired
    public AssignmentServicesImpl(final IAssignmentRespositry assignmentRespositry){
        this.assignmentRespositry = assignmentRespositry;
    }

    @Override
    public Boolean isExist(AssignmentDTO assignmentDTO) {
        return assignmentRespositry.existsById(assignmentDTO.getId());
    }

    @Override
    public List<AssignmentDTO> getAllAssignment() {
        List<Assignment> assignments = assignmentRespositry.findAll();
        List<AssignmentDTO> assignmentsDTO = assignments.stream().map(assignment -> AssignmentMapper.fromEntityToDTO(assignment)).collect(Collectors.toList());
        return assignmentsDTO;
    }

    @Override
    public List<AssignmentDTO> addManyAssignment(List<AssignmentDTO> assignmentsDTO) {
        for (AssignmentDTO assignmentDTO : assignmentsDTO) {
            addAssignment(assignmentDTO);
        }
        return assignmentsDTO;
    }

    @Override
    public AssignmentDTO addAssignment(AssignmentDTO assignmentDTO) {
        if (assignmentDTO == null) {
            throw new RuntimeException("Assignment must be Provided");
        }

        Assignment assignment = AssignmentMapper.fromDTOToEntity(assignmentDTO);

        Assignment savedAssignment = assignmentRespositry.save(assignment);

        return AssignmentMapper.fromEntityToDTO(savedAssignment);
    }

    @Override
    public Optional<AssignmentDTO> getAssignmentById(Long id) {
        Optional<Assignment> assignment = assignmentRespositry.findById(id);

        if (assignment.isPresent()) {
            return Optional.of(AssignmentMapper.fromEntityToDTO(assignment.get()));
        }

        return Optional.empty();
    }

    @Override
    public AssignmentDTO updateAssignment(Long id, AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRespositry.findById(id).orElseThrow();

        if (assignment != null) {
            assignment.setClasses(assignmentDTO.getClasses());
            assignment.setSubjects(assignmentDTO.getSubjects());
            assignment.setTeacher(assignmentDTO.getTeacher());
            assignment.setDueDate(assignmentDTO.getDueDate());
            assignmentRespositry.save(assignment);
        }
        return AssignmentMapper.fromEntityToDTO(assignment);
    }

    @Override
    public void deleteAssignmentById(Long id) {
        assignmentRespositry.deleteById(id);
    }
    
}
