package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.CompanyDto;
import uz.pdp.appcompany.repoistory.AddressRepository;
import uz.pdp.appcompany.repoistory.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyService(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElseGet(Company::new);
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean existsByCorpName = companyRepository.existsByCorpName(companyDto.getCorpName());
        if(existsByCorpName)
            return new ApiResponse("This company name already exists", false);
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if(!optionalAddress.isPresent())
            return new ApiResponse("Address has not been found!", false);
        Address address = optionalAddress.get();
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Company added!", true);
    }

    public ApiResponse editCompanyService(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(!optionalCompany.isPresent())
            return new ApiResponse("Company with this ID doesn't exist!", false);
        boolean existsByCorpNameAndIdNot = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if(existsByCorpNameAndIdNot)
            return new ApiResponse("This company name already exists!", false);
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if(!optionalAddress.isPresent())
            return new ApiResponse("Address has not been found!", false);
        Address address = optionalAddress.get();
        Company company = optionalCompany.get();
        company.setAddress(address);
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ApiResponse("Company has been edited!", true);
    }

    public ApiResponse deleteCompanyService(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company has been deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Company has not been found!", false);
        }
    }
}
