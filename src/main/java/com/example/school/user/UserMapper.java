package com.example.school.user;

public class UserMapper {
    public static User fromDTOToEntity(UserDTO userDTO){
        return User.builder()
            .id(userDTO.getId())
            .first_name(userDTO.getFirst_name())
            .last_name(userDTO.getLast_name())
            .username(userDTO.getUsername())
            
            .build();
    }

    public static UserDTO fromEntityToDTO(User user){
        return UserDTO.builder()
            .id(user.getId())
            .first_name(user.getFirst_name())
            .last_name(user.getLast_name())
            .username(user.getUsername())
            .build();
    }
}
