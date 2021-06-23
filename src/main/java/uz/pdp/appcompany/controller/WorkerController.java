package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Worker;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.WorkerDto;
import uz.pdp.appcompany.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;


    @GetMapping
    public ResponseEntity<List<Worker>> getAllWorkers() {
        List<Worker> allWorkers = workerService.getAllWorkers();
        return ResponseEntity.ok(allWorkers);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Integer id) {
        Worker worker = workerService.getWorkerById(id);
        return ResponseEntity.ok(worker);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addWorker(@Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.addWorkerService(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editWorker(@PathVariable Integer id, @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.editWorkerService(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.deleteWorkerService(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
