package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.payload.AddressDto;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.repoistory.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;


    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    public Address getAddressService(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElseGet(Address::new);
    }

    public ApiResponse addAddressService(AddressDto addressDto) {
        boolean existsByHomeNumber = addressRepository.existsByHomeNumber(addressDto.getHomeNumber());
        if(existsByHomeNumber)
            return new ApiResponse("Address with this home number exists!", false);
        Address address = new Address();
        address.setStreet(addressDto.getHomeNumber());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address added!", true);

    }

    public ApiResponse editAddressController(Integer id, AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(!optionalAddress.isPresent())
            return new ApiResponse("Address with this ID doesn't exist!", false);
        boolean existsByHomeNumberAndIdNot = addressRepository.existsByHomeNumberAndIdNot(addressDto.getHomeNumber(), id);
        if(existsByHomeNumberAndIdNot)
            return new ApiResponse("Address with this home number exists!", false);
        Address address = optionalAddress.get();
        address.setHomeNumber(addressDto.getHomeNumber());
        address.setStreet(addressDto.getStreet());
        addressRepository.save(address);
        return new ApiResponse("Address edited!", true);
    }

    public ApiResponse deleteAddressService(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address deleted!", true);
        } catch (Exception e) {
                return new ApiResponse("Address with this ID not found!!!",false);
        }
    }
}
