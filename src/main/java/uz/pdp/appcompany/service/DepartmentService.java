package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.DepartmentDto;
import uz.pdp.appcompany.repoistory.CompanyRepository;
import uz.pdp.appcompany.repoistory.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentByIdService(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElseGet(Department::new);
    }

    public ApiResponse addDepartmentService(DepartmentDto departmentDto) {
        boolean existsByName = departmentRepository.existsByName(departmentDto.getName());
        if(existsByName)
            return new ApiResponse("This department already exists", false);
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if(!optionalCompany.isPresent())
            return new ApiResponse("Company has not been found!", false);
        Company company = optionalCompany.get();
        Department department = new Department();
        department.setCompany(company);
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("Department Added!", true);
    }

    public ApiResponse editDepartmentService(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if(!optionalDepartment.isPresent())
            return new ApiResponse("Department has not been found!", false);
        boolean existsByNameAndIdNot = departmentRepository.existsByNameAndIdNot(departmentDto.getName(), id);
        if(existsByNameAndIdNot)
            return new ApiResponse("This department name already exists", false);
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if(!optionalCompany.isPresent())
            return new ApiResponse("Company has not been found!", false);
        Company company = optionalCompany.get();
        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department edited!", true);

    }

    public ApiResponse deleteDepartmentService(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department has been deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Department has not been found!", false);
        }
    }
}
