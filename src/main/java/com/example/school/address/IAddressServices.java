package com.example.school.address;

import com.example.school.user.User;
import com.example.school.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IAddressServices {
    
    Boolean isAddressExists(AddressDTO addressDTO);

    AddressDTO addAddress(AddressDTO addressDTO,Long userId);

    AddressDTO addAddress(AddressDTO addressDTO, UserDTO user);

    Optional<AddressDTO> getAddressById(Long id);

    List<AddressDTO> getAllAddress();

    void deleteAddressById(Long id);

    AddressDTO updateAddress(Long id,AddressDTO addressDTO);

    void setUserIdToBeNull(Long id);
}
