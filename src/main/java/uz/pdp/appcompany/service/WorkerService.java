package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.entity.Worker;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.WorkerDto;
import uz.pdp.appcompany.repoistory.AddressRepository;
import uz.pdp.appcompany.repoistory.DepartmentRepository;
import uz.pdp.appcompany.repoistory.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElseGet(Worker::new);
    }

    public ApiResponse addWorkerService(WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if(existsByPhoneNumber)
            return new ApiResponse("This phone number already exists!", false);
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if(optionalAddress.isEmpty())
            return new ApiResponse("This address doesn't exist!", false);
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if(optionalDepartment.isEmpty())
            return new ApiResponse("This department doesn't exist!", false);
        Address address = optionalAddress.get();
        Department department = optionalDepartment.get();
        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(address);
        worker.setDepartment(department);
        workerRepository.save(worker);
        return new ApiResponse("Worker added!", true);
    }


    public ApiResponse editWorkerService(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if(optionalWorker.isEmpty())
            return new ApiResponse("Worker has not been found!", false);
        boolean existsByPhoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if(existsByPhoneNumberAndIdNot)
            return new ApiResponse("This phone number already exists", false);
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if(optionalAddress.isEmpty())
            return new ApiResponse("This address doesn't exist!", false);
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if(optionalDepartment.isEmpty())
            return new ApiResponse("This department doesn't exist!", false);
        Address address = optionalAddress.get();
        Department department = optionalDepartment.get();
        Worker worker = optionalWorker.get();
        worker.setDepartment(department);
        worker.setAddress(address);
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        workerRepository.save(worker);
        return new ApiResponse("Worker edited!", true);
    }

    public ApiResponse deleteWorkerService(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker has been deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Worker has not been found!", false);
        }
    }
}
