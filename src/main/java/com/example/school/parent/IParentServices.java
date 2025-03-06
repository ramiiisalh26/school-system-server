package com.example.school.parent;

import java.util.List;
import java.util.Optional;

public interface IParentServices {
    
    Boolean isExists(ParentDTO parentDTO);

    List<ParentDTO> getAllParent();

    List<ParentDTO> addManyParent(List<ParentDTO> parentsDTO);

    ParentDTO addParent(ParentDTO parentDTO);

    ParentDTO updateParent(Long id, ParentDTO parentDTO);

    Optional<ParentDTO> getParentById(Long Id);

    void deleteById(Long id);

}
