package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
    @NotNull(message = "Name shouldn't be empty")
    private String name;

    @NotNull(message = "Phone number shouldn't be empty")
    private String phoneNumber;

    @NotNull(message = "Address shouldn't be empty")
    private Integer addressId;

    @NotNull(message = "Department shouldn't be empty")
    private Integer departmentId;
}
