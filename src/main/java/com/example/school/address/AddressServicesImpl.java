package com.example.school.address;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.school.user.IUserServices;
import com.example.school.user.User;
import com.example.school.user.UserDTO;
import com.example.school.user.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServicesImpl implements IAddressServices{

    private final IAddressRespositry addressRespositry;
    private final IUserServices IuserServices;

    @Autowired
    public AddressServicesImpl(final IAddressRespositry addressRespositry,final IUserServices IuserServices)
    {
        this.addressRespositry = addressRespositry;
        this.IuserServices = IuserServices;
    }

    @Override
    public Boolean isAddressExists(AddressDTO addressDTO) {
        return addressRespositry.existsById(addressDTO.getId());
    }

    @Override
    public AddressDTO addAddress(AddressDTO addressDTO,Long userId) {
        if (addressDTO == null) {
            throw new RuntimeException("Address must be provided");
        }

        Address address = Address.builder()
                .city(addressDTO.getCity())
                .country(addressDTO.getCountry())
                .state(addressDTO.getState())
                .street(addressDTO.getStreet())
                .zip(addressDTO.getZip())
                .build();

        Address savedAddress = addressRespositry.save(address);

        return AddressMapper.fromEntityToDTO(savedAddress);
    }

    @Transactional
    public  AddressDTO addAddress(AddressDTO addressDTO, UserDTO userDTO){
        if (addressDTO == null){
            throw new RuntimeException("Address must be provided");
        }
//        addressDTO.setUserDTO(userDTO);
//        System.out.println(userDTO);
        Address address = AddressMapper.fromDTOToEntity(addressDTO);
        System.out.println(address);
        Address savedAddress = addressRespositry.save(address);
        System.out.println(savedAddress);
        return AddressMapper.fromEntityToDTO(savedAddress);
    }

    @Override
    public Optional<AddressDTO> getAddressById(Long id) {
        Optional<Address> foundedAddress = addressRespositry.findById(id);

        if (foundedAddress.isEmpty()) return Optional.empty();

        return Optional.of(AddressMapper.fromEntityToDTO(foundedAddress.get()));

    }

    @Override
    public List<AddressDTO> getAllAddress() {
        List<Address> addresses = addressRespositry.findAll();
        return addresses.stream().map(AddressMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAddressById(Long id) {
        addressRespositry.deleteById(id);
    }

    @Override
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address address = addressRespositry.findById(id).orElseThrow();

        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setState(addressDTO.getState());
        address.setStreet(addressDTO.getStreet());
        address.setZip(addressDTO.getZip());
        address.setUser(address.getUser());
        addressRespositry.save(address);

        return AddressMapper.fromEntityToDTO(address);
    }

    @Override
    public void setUserIdToBeNull(Long id){
        addressRespositry.setUserIdToBeNull(id);
    }
    
}
