package com.example.school.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    
    private final IAddressServices addressServices;

    @Autowired
    public AddressController(final IAddressServices addressServices){
        this.addressServices = addressServices;
    }

//    @PostMapping(path = "/add")
//    public ResponseEntity<AddressDTO> addAddress(@RequestBody final AddressDTO addressDTO){
//        AddressDTO savedAddress = addressServices.addAddress(addressDTO);
//        return new ResponseEntity<AddressDTO>(savedAddress,HttpStatus.CREATED);
//    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable final Long id){
        Optional<AddressDTO> addressDTO = addressServices.getAddressById(id);
        if (addressDTO.isPresent()) {
            return new ResponseEntity<AddressDTO>(addressServices.getAddressById(id).get(),HttpStatus.OK);
        }

        return new ResponseEntity<AddressDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<AddressDTO>> getAllAddress(){
        return new ResponseEntity<List<AddressDTO>>(addressServices.getAllAddress(),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<AddressDTO> deleteAddressById(@PathVariable final Long id){
        addressServices.deleteAddressById(id);
        return new ResponseEntity<AddressDTO>(HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable final Long id, @RequestBody final AddressDTO addressDTO){
        final boolean isExist = addressServices.isAddressExists(addressDTO);
        if (isExist) {
            final AddressDTO updateAddress = addressServices.updateAddress(id, addressDTO);
            return new ResponseEntity<AddressDTO>(updateAddress,HttpStatus.OK);
        }
        return new ResponseEntity<AddressDTO>(HttpStatus.NO_CONTENT);
    }

}
