package com.example.school.parent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.school.address.Address;
import com.example.school.address.IAddressRespositry;
import com.example.school.student.IStudentRepositry;
import com.example.school.student.Student;

@Service
public class ParentServicesImpl implements IParentServices{

    private IParentRepositry parentRepositry;
    private IStudentRepositry studentRepositry;
    private IAddressRespositry addressRespositry;

    @Autowired
    public ParentServicesImpl(
        final IParentRepositry parentRepositry,
        final IStudentRepositry studentRepositry,
        final IAddressRespositry addressRespositry
    ){
        this.parentRepositry = parentRepositry;
        this.studentRepositry = studentRepositry;
        this.addressRespositry = addressRespositry;
    }

    @Override
    public Boolean isExists(ParentDTO parentDTO) {
        return parentRepositry.existsById(parentDTO.getId()); 
    }

    @Override
    public List<ParentDTO> getAllParent() {
        List<Parent> parents = parentRepositry.findAll();
        List<ParentDTO> parentsDTO = parents.stream().map(parent -> ParentMapper.fromEntityToDTO(parent)).collect(Collectors.toList());
        return parentsDTO;
    }

    @Override
    public List<ParentDTO> addManyParent(List<ParentDTO> parentsDTO) {
        for (ParentDTO parentDTO : parentsDTO) {
            addParent(parentDTO);
        }
        return parentsDTO;
    }

    @Override
    public ParentDTO addParent(ParentDTO parentDTO) {
        if (parentDTO == null) {
            throw new RuntimeException("Must be provided parent");
        }

        Parent parent = ParentMapper.fromDTOToEntity(parentDTO);

        Parent savedParent = parentRepositry.save(parent);

        return ParentMapper.fromEntityToDTO(savedParent);
    }

    @Override
    public ParentDTO updateParent(Long id, ParentDTO parentDTO) {
        Parent parent = parentRepositry.findById(id).orElseThrow();
        Student student = studentRepositry.findById(id).orElseThrow();
        Address address = addressRespositry.findById(id).orElseThrow();
    
        if (parent != null) {
            parent.setName(parentDTO.getName());
            parent.setEmail(parentDTO.getEmail());
            parent.setPhone(parentDTO.getPhone());
        }

        int sizeOfStudent = parentDTO.getStudent().size();
        
        for(int i=0;i<sizeOfStudent;i++){
            student.setName(parentDTO.getStudent().get(i).getName());
            student.setEmail(parentDTO.getStudent().get(i).getEmail());
            student.setPhone(parentDTO.getStudent().get(i).getPhone());
            student.setPhoto(parentDTO.getStudent().get(i).getPhoto());
        }
        
        if (address != null) {
            address.setCity(parentDTO.getAddress().getCity());
            address.setCountry(parentDTO.getAddress().getCountry());
            address.setState(parentDTO.getAddress().getState());
            address.setStreet(parentDTO.getAddress().getStreet());
            address.setZip(parentDTO.getAddress().getZip());
        }
        return ParentMapper.fromEntityToDTO(parent);
    }

    @Override
    public Optional<ParentDTO> getParentById(Long Id) {
        Optional<Parent> parent = parentRepositry.findById(Id);

        if (parent.isPresent()) {
            return Optional.of(ParentMapper.fromEntityToDTO(parent.get()));
        }

        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        parentRepositry.deleteById(id);
    }
    
}
