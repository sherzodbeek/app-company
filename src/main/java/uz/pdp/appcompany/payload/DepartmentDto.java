package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotNull(message = "Department's name shouldn't be empty")
    private String name;

    @NotNull(message = "Department's company shouldn't be empty")
    private Integer companyId;
}
