package com.example.school.address;

import java.util.List;
import java.util.Optional;

public interface IAddressServices {
    
    Boolean isAddressExists(AddressDTO addressDTO);

    AddressDTO addAddress(AddressDTO addressDTO);

    Optional<AddressDTO> getAddressById(Long id);

    List<AddressDTO> getAllAddress();

    void deleteAddressById(Long id);

    AddressDTO updateAddress(Long id,AddressDTO addressDTO);

    void setUserIdToBeNull(Long id);
}
