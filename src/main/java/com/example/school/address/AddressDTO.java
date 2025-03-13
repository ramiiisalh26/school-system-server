package com.example.school.address;

import com.example.school.user.UserDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    Long id;
    String street;
    String city;
    String state;
    String zip;
    String country;
//    UserDTO userDTO;
}
