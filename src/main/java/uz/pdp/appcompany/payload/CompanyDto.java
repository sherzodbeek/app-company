package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {
    @NotNull(message = "Company name shouldn't be empty!")
    private String corpName;
    @NotNull(message = "Director name shouldn't be empty!")
    private String directorName;
    @NotNull(message = "Address shouldn't be empty!")
    private Integer addressId;
}
