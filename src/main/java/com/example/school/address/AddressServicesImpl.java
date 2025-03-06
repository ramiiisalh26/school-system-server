package com.example.school.address;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServicesImpl implements IAddressServices{

    private IAddressRespositry addressRespositry;

    @Autowired
    public AddressServicesImpl(IAddressRespositry addressRespositry){
        this.addressRespositry = addressRespositry;
    }

    @Override
    public Boolean isAddressExists(AddressDTO addressDTO) {
        return addressRespositry.existsById(addressDTO.getId());
    }

    @Override
    public AddressDTO addAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            throw new RuntimeException("Address must be provided");
        }
        
        Address address = AddressMapper.fromDTOToEntity(addressDTO);

        Address savedAddress = addressRespositry.save(address);

        return AddressMapper.fromEntityToDTO(savedAddress);

    }

    @Override
    public Optional<AddressDTO> getAddressById(Long id) {
        Optional<Address> foundedAddress = addressRespositry.findById(id);

        if (foundedAddress.isPresent()) {
            return Optional.of(AddressMapper.fromEntityToDTO(foundedAddress.get()));
        }

        return Optional.empty();
    }

    @Override
    public List<AddressDTO> getAllAddress() {
        List<Address> addresses = addressRespositry.findAll();
        List<AddressDTO> addressDTOs = addresses.stream().map(address -> AddressMapper.fromEntityToDTO(address)).collect(Collectors.toList());
        return addressDTOs;
    }

    @Override
    public void deleteAddressById(Long id) {
        addressRespositry.deleteById(id);
    }

    @Override
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address address = addressRespositry.findById(id).orElseThrow();

        if (address != null) {
            address.setCity(addressDTO.getCity());
            address.setCountry(addressDTO.getCountry());
            address.setState(addressDTO.getState());
            address.setStreet(addressDTO.getStreet());
            address.setZip(addressDTO.getZip());
            address.setUser(address.getUser());
            addressRespositry.save(address);
        }

        return AddressMapper.fromEntityToDTO(address);
    }

    @Override
    public void setUserIdToBeNull(Long id){
        addressRespositry.setUserIdToBeNull(id);
    }
    
}
