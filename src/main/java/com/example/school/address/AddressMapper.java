package com.example.school.address;

import com.example.school.user.UserMapper;

public class AddressMapper {
    public static Address fromDTOToEntity(AddressDTO addressDTO){
        return Address.builder()
            .id(addressDTO.getId())
            .street(addressDTO.getStreet())
            .city(addressDTO.getCity())
            .state(addressDTO.getState())
            .country(addressDTO.getCountry())
            .zip(addressDTO.getZip())
//             .user(UserMapper.fromDTOToEntity(addressDTO.getUserDTO()))
            .build();
    }

    public static AddressDTO fromEntityToDTO(Address address){
        return AddressDTO.builder()
            .id(address.getId())
            .street(address.getStreet())
            .city(address.getCity())
            .state(address.getState())
            .country(address.getCountry())
            .zip(address.getZip())
//             .userDTO(UserMapper.fromEntityToDTO(address.getUser()))
            .build();
    }
}
