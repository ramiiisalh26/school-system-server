package com.example.school.user;

import com.example.school.address.AddressDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    Long id;
    String first_name;
    String last_name;
    String username;
    AddressDTO addressDTO;
}
